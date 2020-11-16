package com.godofwibu.narga.services;
import java.sql.Date;
import java.util.List;

import com.godofwibu.narga.dto.Ticket_DTO_API_GetByIssue;
import com.godofwibu.narga.entities.Issue;
import com.godofwibu.narga.formdata.NewIssueFormData;

public interface IIssueService {
	void newIssues(NewIssueFormData formData) throws ServiceLayerException;
	List<Issue> getIssuesInThisWeek() throws ServiceLayerException;
	String getAllTicketOfGivenIssueAsJson(Integer issueId);
	String getAllIssuesByGivenFilmIdAndDateAsJson(int filmId, Date date) throws ServiceLayerException;
}

