package com.mycompany.nbastats.importer.nbastats.importer.model.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.mycompany.nbastats.importer.nbastats.importer.model.entities.MatchGame;

public interface MatchCreatorService {

	void insertMatches(List<MatchGame> matches) throws Exception;

	Calendar findLastMatchImportedDate();

	void insertMatch(MatchGame match);
}
