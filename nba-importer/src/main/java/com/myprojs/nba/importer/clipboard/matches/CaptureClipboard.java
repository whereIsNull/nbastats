package com.myprojs.nba.importer.clipboard.matches;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.myprojs.nba.importer.matches.MatchesByDayImporter;
import com.myprojs.nba.importer.model.entities.MatchGame;
import com.myprojs.nba.importer.model.services.MatchCreatorService;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class CaptureClipboard extends Application {

	private String content;
	private MatchesByDayImporter importer = new MatchesByDayImporter();
	private MatchCreatorService matchCreatorService;

	@Override
	public void start(Stage stage) throws Exception {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
		matchCreatorService = context.getBean("matchCreatorService", MatchCreatorService.class);
		
		stage.setTitle("My First JavaFX App");
		Label label = new Label("Vamos a importar datos!!");
        Scene scene = new Scene(label, 400, 200);
        stage.setScene(scene);
		stage.show();		
		setClipboardListener();
	}
	
	void setClipboardListener() {
		final Clipboard systemClipboard = Clipboard.getSystemClipboard();

        new com.sun.glass.ui.ClipboardAssistance(com.sun.glass.ui.Clipboard.SYSTEM) {
            @Override
            public void contentChanged() {
            	List<MatchGame> matchesByDay = null;
                System.out.print("System clipboard content changed: ");
                if ( systemClipboard.hasImage() ) {
                    System.out.println("image");
                } else if ( systemClipboard.hasString() ) {
                    System.out.println("string");
                    String newContent = systemClipboard.getString();
        			if(content == null || !content.equals(newContent)) {
        				content = newContent;
        				System.out.println(content);
        				matchesByDay = importer.extractMatchList(content);
        			}
                } else if ( systemClipboard.hasFiles() ) {
                    System.out.println("files");
                } else if ( systemClipboard.hasHtml() ) {
                    System.out.println("html");
                    String newContent = systemClipboard.getHtml();
        			if(content == null || !content.equals(newContent)) {
        				content = newContent;
        				System.out.println(content);
        				matchesByDay = importer.extractMatchList(content);
        			}
                }
                if(matchesByDay != null) {
                	saveMatches(matchesByDay);
                }
            }
        };
	}
	
	void saveMatches(List<MatchGame> matchesByDay) {
		try {
			matchCreatorService.insertMatches(matchesByDay);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static void main(String args[]) {
		launch(args);
	}
}
