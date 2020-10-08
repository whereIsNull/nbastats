package com.myprojs.nba.importer.model.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.myprojs.nba.importer.model.entities.MatchGame;

public interface MatchCreatorService {

	void insertMatches(List<MatchGame> matches) throws Exception;

//	Calendar findLastMatchImportedDate();

	void insertMatch(MatchGame match);
}
