package com.myprojs.nba.importer.model.repositories;

import com.myprojs.nba.importer.model.entities.MatchGame;

public interface MatchDao {

	public void insertMatch(MatchGame match) throws Exception;
}
