package com.mycompany.nbastats.importer.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Generated;

@Entity
public class MatchTeamsScoreQuarter {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Integer quarterIndex;
	private Integer quarterHomeScore;
	private Integer quarterVisitorScore;
	
	@ManyToOne
	private MatchGame matchGame;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getQuarterIndex() {
		return quarterIndex;
	}
	public void setQuarterIndex(Integer quarterIndex) {
		this.quarterIndex = quarterIndex;
	}
	public Integer getQuarterHomeScore() {
		return quarterHomeScore;
	}
	public void setQuarterHomeScore(Integer quarterHomeScore) {
		this.quarterHomeScore = quarterHomeScore;
	}
	public Integer getQuarterVisitorScore() {
		return quarterVisitorScore;
	}
	public void setQuarterVisitorScore(Integer quarterVisitorScore) {
		this.quarterVisitorScore = quarterVisitorScore;
	}
	public MatchGame getMatchGame() {
		return matchGame;
	}
	public void setMatchGame(MatchGame matchGame) {
		this.matchGame = matchGame;
	}
	@Override
	public String toString() {
		return String.valueOf(quarterHomeScore + "-" + quarterVisitorScore);
	}
}
