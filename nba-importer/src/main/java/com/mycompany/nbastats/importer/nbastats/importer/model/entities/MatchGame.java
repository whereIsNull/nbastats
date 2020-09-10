package com.mycompany.nbastats.importer.nbastats.importer.model.entities;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MatchGame {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String homeAbr;
	private String visitorAbr;
	private String homeLink;
	private String visitorLink;
	private Integer homeReadScore;
	private Integer visitorReadScore;
	private String boxScoreLink;
	private String matchPdfUrl;
	
	@OneToMany
	private List<MatchTeamsScoreQuarter> scoreByQuarter;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHomeAbr() {
		return homeAbr;
	}
	public void setHomeAbr(String homeAbr) {
		this.homeAbr = homeAbr;
	}
	public String getVisitorAbr() {
		return visitorAbr;
	}
	public void setVisitorAbr(String visitorAbr) {
		this.visitorAbr = visitorAbr;
	}
	public String getHomeLink() {
		return homeLink;
	}
	public void setHomeLink(String homeLink) {
		this.homeLink = homeLink;
	}
	public String getVisitorLink() {
		return visitorLink;
	}
	public void setVisitorLink(String visitorLink) {
		this.visitorLink = visitorLink;
	}
	public Integer getHomeReadScore() {
		return homeReadScore;
	}
	public void setHomeReadScore(Integer homeReadScore) {
		this.homeReadScore = homeReadScore;
	}
	public Integer getVisitorReadScore() {
		return visitorReadScore;
	}
	public void setVisitorReadScore(Integer visitorReadScore) {
		this.visitorReadScore = visitorReadScore;
	}

	public List<MatchTeamsScoreQuarter> getScoreByQuarter() {
		return scoreByQuarter;
	}
	public void setScoreByQuarter(List<MatchTeamsScoreQuarter> scoreByQuarter) {
		this.scoreByQuarter = scoreByQuarter;
	}
	
	public String getMatchPdfUrl() {
		return matchPdfUrl;
	}
	public void setMatchPdfUrl(String matchPdfUrl) {
		this.matchPdfUrl = matchPdfUrl;
	}
	
	public String getBoxScoreLink() {
		return boxScoreLink;
	}
	
	public void setBoxScoreLink(String boxScoreLink) {
		this.boxScoreLink = boxScoreLink;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(homeLink + ": ");
		int totalHome = 0;
		for(MatchTeamsScoreQuarter score: scoreByQuarter) {
			result.append(score + "/");
			totalHome = totalHome + (score.getQuarterHomeScore()!=null?score.getQuarterHomeScore():0);
		}
		result.append(" " + totalHome + ", " + this.homeReadScore);
		result.append("\n");
		result.append(visitorLink + ": ");
		int totalVisitor = 0;
		for(MatchTeamsScoreQuarter score: scoreByQuarter) {
			result.append(score + "/");
			totalVisitor = totalVisitor + (score.getQuarterVisitorScore()!=null?score.getQuarterVisitorScore():0);
		}
		result.append(" " + totalVisitor + ", " + this.visitorReadScore);
		return result.toString();
	}
}
