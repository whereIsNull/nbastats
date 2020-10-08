package com.myprojs.nba.importer.model.repositories;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.SessionFactory;

import com.myprojs.nba.importer.model.entities.MatchGame;
import com.myprojs.nba.importer.model.entities.MatchTeamsScoreQuarter;

public class MySQLMatchDao implements MatchDao {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void insertMatch(MatchGame match) throws Exception {
		this.sessionFactory.getCurrentSession().save(match);
		for(MatchTeamsScoreQuarter scoreQuarter: match.getScoreByQuarter()) {
			insertScoreQuarter(scoreQuarter);
		}
	}

	public void insertScoreQuarter(MatchTeamsScoreQuarter scoreQuarter) {
		this.sessionFactory.getCurrentSession().save(scoreQuarter);
	}

	public Calendar findLastMatchImportedDate() {
		return (Calendar)this.sessionFactory.getCurrentSession().createQuery("select max(matchDate) from MatchGame").uniqueResult();
	}
}
