package com.godofwibu.narga.repositories;

import java.sql.Date;
import java.util.List;

import com.godofwibu.narga.entities.Issue;

public interface IIssueRepository extends ICurdRepository<Issue, Integer>{
	List<Issue> getIssueByDateBetween(Date begin, Date end);

}
