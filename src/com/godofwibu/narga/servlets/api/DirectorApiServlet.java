package com.godofwibu.narga.servlets.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "directorApi", urlPatterns = {"/api/director/*"})
public class DirectorApiServlet extends ApiServlet {
	private static final long serialVersionUID = 1L;
       
    public DirectorApiServlet() { }

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = getAction(req);
		switch (action) {
		case "all":
			getAll(res);
			break;
		case "search":
			search(res, req.getParameter("input"));
			break;
		default:
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}


	private void search(HttpServletResponse res, String parameter) {
		
	}


	private void getAll(HttpServletResponse res) {
		
	}

}
