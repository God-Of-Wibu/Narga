package com.godofwibu.narga.servlets.api;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.godofwibu.narga.services.IDirectorService;

@WebServlet(name = "directorApi", urlPatterns = {"/api/director/*"})
public class DirectorApiServlet extends ApiServlet {
	private static final long serialVersionUID = 1L;
	
	private IDirectorService directorService;
   
	@Override
	public void init() throws ServletException {
		super.init();
		
		directorService = getAttributeByClassName(IDirectorService.class);
		addAction("search", this::search);	
	}
	
	public String search(HttpServletRequest req) {
		return directorService.search(req.getParameter("input"));
	}
}
