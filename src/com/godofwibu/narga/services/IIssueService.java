package com.godofwibu.narga.services;
import java.util.List;

import com.godofwibu.narga.dto.NewIssueFormData;
import com.godofwibu.narga.entities.Issue;

public interface IIssueService {
	void newIssues(NewIssueFormData formData) throws ServiceLayerException;
	List<Issue> getIssuesInThisWeek() throws ServiceLayerException;
}

