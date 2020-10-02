package com.mycompany.nbastats.importer.model.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.nbastats.importer.model.entities.MatchGame;
import com.mycompany.nbastats.importer.model.entities.MatchTeamsScoreQuarter;
import com.mycompany.nbastats.importer.model.repositories.MatchDao;
import com.mycompany.nbastats.importer.model.repositories.MySQLMatchDao;

public class MatchCreatorServiceImpl implements MatchCreatorService {

	private Map<String, MatchDao> matchDao;
	
	public void setMatchDao(Map<String, MatchDao> matchDao) {
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
		String dbSystem=System.getProperty("DBSystem");
		MatchDao matchDaoSelected = matchDao.get(dbSystem);
		try {
			matchDaoSelected.insertMatch(match);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
//	@Override
//	@Transactional(readOnly=true)
//	public Calendar findLastMatchImportedDate() {
//		return matchDao.findLastMatchImportedDate();
//	}
}
