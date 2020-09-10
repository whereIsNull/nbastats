package com.mycompany.nbastats.importer.nbastats.importer.model.repositories;

import java.util.Calendar;
import java.util.Date;

import org.hibernate.SessionFactory;

import com.mycompany.nbastats.importer.nbastats.importer.model.entities.MatchGame;
import com.mycompany.nbastats.importer.nbastats.importer.model.entities.MatchTeamsScoreQuarter;

public class MatchDao {

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void insertMatch(MatchGame match) throws Exception {
		this.sessionFactory.getCurrentSession().save(match);		
	}

	public void insertScoreQuarter(MatchTeamsScoreQuarter scoreQuarter) {
		this.sessionFactory.getCurrentSession().save(scoreQuarter);
	}

	public Calendar findLastMatchImportedDate() {
		return (Calendar)this.sessionFactory.getCurrentSession().createQuery("select max(matchDate) from MatchGame").uniqueResult();
	}
}
