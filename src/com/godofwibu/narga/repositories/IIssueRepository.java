package com.godofwibu.narga.repositories;

import java.sql.Date;
import java.util.List;

import com.godofwibu.narga.entities.Issue;
import com.google.gson.JsonElement;

public interface IIssueRepository extends ICurdRepository<Issue, Integer>{
	List<Issue> findByDateBetween(Date begin, Date end);

	List<Issue> findByFilmIdAndDate(int filmId, Date date);

}
