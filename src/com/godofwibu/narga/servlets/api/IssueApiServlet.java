package com.godofwibu.narga.servlets.api;

import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.godofwibu.narga.services.IIssueService;

@WebServlet("/api/issue/*")
public class IssueApiServlet extends ApiServlet {
	
	private IIssueService issueService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		issueService = getAttributeByClassName(IIssueService.class);
		addAction("get", this::get);
	}

	private String get(HttpServletRequest req) {
		int filmId = Integer.parseInt(req.getParameter("filmId"));
		Date date = Date.valueOf(req.getParameter("date"));
		return issueService.getAllIssuesByGivenFilmIdAndDateAsJsonArray(filmId, date);
	}
}
