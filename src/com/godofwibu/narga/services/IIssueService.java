package com.godofwibu.narga.services;
import java.sql.Date;
import java.util.List;

import com.godofwibu.narga.entities.Issue;
import com.godofwibu.narga.formdata.NewIssueFormData;
import com.godofwibu.narga.services.exception.ServiceLayerException;

public interface IIssueService {
	void newIssues(NewIssueFormData formData) throws ServiceLayerException;
	String getAllTicketsOfGivenIssueAsJsonArray(Integer issueId);
	String getAllIssuesByGivenFilmIdAndDateAsJsonArray(int filmId, Date date) throws ServiceLayerException;
}

