package com.godofwibu.narga.services;

import java.util.List;

import com.godofwibu.narga.entities.Issue;

public interface IIssueService {
		List<Issue> getIssuesInThisWeek() throws ServiceLayerException;
	
}
