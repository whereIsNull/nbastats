package com.mycompany.nbastats.importer.model.repositories;

import com.mycompany.nbastats.importer.model.entities.MatchGame;

public interface MatchDao {

	public void insertMatch(MatchGame match) throws Exception;
}
