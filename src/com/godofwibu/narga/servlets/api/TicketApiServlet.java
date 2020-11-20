package com.godofwibu.narga.servlets.api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.godofwibu.narga.services.IIssueService;

@WebServlet("/api/ticket/*")
public class TicketApiServlet extends ApiServlet {
	private IIssueService issueService;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		issueService = getAttributeByClassName(IIssueService.class);
		addAction("get-by-issue-id", this::getByIssue);
	}

	private String getByIssue(HttpServletRequest req) {
		return issueService.getAllTicketsOfGivenIssueAsJsonArray(Integer.parseInt( req.getParameter("issueId")) );
	}
}
