package com.myprojs.nba.importer.model.repositories;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.myprojs.nba.importer.model.entities.MatchGame;
import com.myprojs.nba.importer.model.entities.MatchTeamsScoreQuarter;

public class MongoDBMatchDao implements MatchDao {

	@Override
	public void insertMatch(MatchGame match) throws Exception {
//		MongoClient mongoClient = new MongoClient("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&ssl=false");
		
		MongoClient mongoClient = new MongoClient("localhost",27017);
		MongoDatabase db = mongoClient.getDatabase("nbaStats");
		MongoCollection<Document> matchesDB = db.getCollection("matches");
		
		Document basicObject = new Document();
		basicObject.put("boxScoreLink", match.getBoxScoreLink());
		basicObject.put("homeAbr", match.getHomeAbr());
		basicObject.put("visitorAbr", match.getVisitorAbr());
		basicObject.put("matchPdfUrl", match.getMatchPdfUrl());
		basicObject.put("homeLink", match.getHomeLink());
		basicObject.put("visitorLink", match.getVisitorLink());
		basicObject.put("scoreByQuarter", getMongoScoreByQuarter(match.getScoreByQuarter()));
		
		
		matchesDB.insertOne(basicObject);
	}

	private Object getMongoScoreByQuarter(List<MatchTeamsScoreQuarter> scoreByQuarter) {
		List<Document> scores = new ArrayList<Document>();
		for(MatchTeamsScoreQuarter score: scoreByQuarter) {
			Document quarterScore = new Document();
			quarterScore.put("quarterHomeScore", score.getQuarterHomeScore());
			quarterScore.put("quarterVisitorScore", score.getQuarterVisitorScore());
			quarterScore.put("quarterIndex", score.getQuarterIndex());
			scores.add(quarterScore);
		}
		return scores;
	}
}
