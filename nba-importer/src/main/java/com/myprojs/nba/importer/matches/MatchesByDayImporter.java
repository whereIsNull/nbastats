package com.myprojs.nba.importer.matches;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mycompany.nbastats.importer.model.entities.MatchGame;
import com.mycompany.nbastats.importer.model.entities.MatchTeamsScoreQuarter;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MatchesByDayImporter {
	
    final WebView browser = new WebView();
//    final WebEngine webEngine = browser.getEngine();
     
    public List<MatchGame> importMatches(WebEngine webEngine) {
        
    	System.out.println(webEngine.getDocument().getBaseURI());
    	Object gameSummaries = webEngine.executeScript("document.getElementsByClassName('linescores-container')[0].innerHTML");
    	
    	return extractMatchList(gameSummaries.toString());
    }

	public List<MatchGame> extractMatchList(String htmlString) {
		Document docMatch = Jsoup.parse(htmlString);
    	List<MatchGame> result = new ArrayList<MatchGame>();
    	
//    	String matchUrl = docMatch.getElementsByAttributeValue("rel", "canonical").get(0).attr("href");
    	
    	Elements docMatchElements = docMatch.getElementsByClass("linescores");
    	for(Element matchElement: docMatchElements) {
    		MatchGame match = new MatchGame();
    		String matchPdfUrl = docMatch.getElementsByAttributeValue("data-ng-show", "game.gameLinks.pdf.display").get(0).attr("href");
    		match.setMatchPdfUrl(matchPdfUrl);
    		Elements matches = matchElement.getElementsByClass("linescores-table");
    		for(Element matchScore: matches) {
    			//Home
    			Element teamHomeElement = matchScore.getElementsByTag("tr").get(2);
    			String home = teamHomeElement.getElementsByTag("a").get(0).attr("href").toString();
    			Elements scoresByQuarterHomeTeamsElements = teamHomeElement.getElementsByClass("qtr");
    			Integer homeReadScore = Integer.valueOf(teamHomeElement.getElementsByClass("final").get(0).html());
    			String homeName = teamHomeElement.getElementsByClass("team-name").get(0).html();
    			String homePORecord = teamHomeElement.getElementsByClass("team-record").get(0).html();
    			ArrayList<MatchTeamsScoreQuarter> quarters = new ArrayList<MatchTeamsScoreQuarter>(scoresByQuarterHomeTeamsElements.size());
    			int index = 0;
    			for(Element quarterByHomeTeamElement: scoresByQuarterHomeTeamsElements) {
    				MatchTeamsScoreQuarter quarter = new MatchTeamsScoreQuarter();
    				quarter.setQuarterIndex(index++);
    				try {
    					quarter.setQuarterHomeScore(Integer.valueOf(quarterByHomeTeamElement.html()));
    				} catch(NumberFormatException e) {}
    				quarter.setMatchGame(match);
    				quarters.add(quarter);
    			}
    			match.setHomeLink(home);
    			match.setScoreByQuarter(quarters);
    			match.setHomeReadScore(homeReadScore);
    			match.setHomeAbr(homeName);
    			match.setHomePORecord(homePORecord);
    			
    			//Visitor
    			Element teamVisitorElement = matchScore.getElementsByTag("tr").get(1);
    			String visitor = teamVisitorElement.getElementsByTag("a").attr("href").toString();
    			Elements scoresByQuarterVisitorTeamsElements = teamVisitorElement.getElementsByClass("qtr");
    			Integer visitorReadScore = Integer.valueOf(teamVisitorElement.getElementsByClass("final").get(0).html());
    			String visitorName = teamVisitorElement.getElementsByClass("team-name").get(0).html();
    			String visitorPORecord = teamVisitorElement.getElementsByClass("team-record").get(0).html();
    			index = 0;
    			for(Element quarterByVisitorTeamElement: scoresByQuarterVisitorTeamsElements) {
    				MatchTeamsScoreQuarter quarter = new MatchTeamsScoreQuarter();
    				try {
	    				match.getScoreByQuarter().get(index).setQuarterVisitorScore(Integer.valueOf(quarterByVisitorTeamElement.html()));
	    			} catch(NumberFormatException e) {}
    				quarter.setQuarterIndex(index++);
    			}
    			match.setVisitorLink(visitor);
    			match.setVisitorReadScore(visitorReadScore);
    			match.setVisitorAbr(visitorName);
    			match.setVisitorPORecord(visitorPORecord);
    			
    			match.setMatchPdfUrl(matchElement.getElementsByAttributeValue("data-ng-show", "game.gameLinks.pdf.display").get(0).attr("href"));
    			
    			System.out.println(match);
    			System.out.println();
    			
    			
    		}
    		// get box score
			Elements boxScores = matchElement.getElementsByClass("bottom-bar-container");
			System.out.println("boxScoresLink: " + boxScores.toString());
//			String boxScore = boxScores.get(0).getElementsByTag("a").get(0).attr("abs:href");
			String boxScore = boxScores.get(0).getElementsByTag("a").get(0).attributes().get("href");
			match.setBoxScoreLink(boxScore);
			
			result.add(match);
    	}
    	return result;
	}
}