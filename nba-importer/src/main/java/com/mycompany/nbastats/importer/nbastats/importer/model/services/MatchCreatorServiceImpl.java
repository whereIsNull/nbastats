package com.mycompany.nbastats.importer.nbastats.importer.model.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.nbastats.importer.nbastats.importer.model.entities.MatchGame;
import com.mycompany.nbastats.importer.nbastats.importer.model.entities.MatchTeamsScoreQuarter;
import com.mycompany.nbastats.importer.nbastats.importer.model.repositories.MatchDao;

public class MatchCreatorServiceImpl implements MatchCreatorService {

	private MatchDao matchDao;
	
	public void setMatchDao(MatchDao matchDao) {
		this.matchDao = matchDao;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void insertMatches(List<MatchGame> matches) throws Exception {
		for(MatchGame match: matches) {
			insertMatch(match);
		}		
	}
	
	
	@Override
	public void insertMatch(MatchGame match) {
		try {
			matchDao.insertMatch(match);
			for(MatchTeamsScoreQuarter scoreQuarter: match.getScoreByQuarter()) {
				matchDao.insertScoreQuarter(scoreQuarter);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Override
	@Transactional(readOnly=true)
	public Calendar findLastMatchImportedDate() {
		return matchDao.findLastMatchImportedDate();
	}
}
