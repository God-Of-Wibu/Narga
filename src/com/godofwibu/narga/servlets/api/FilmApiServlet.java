package com.godofwibu.narga.servlets.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.godofwibu.narga.services.IFilmService;
import com.godofwibu.narga.services.ServiceLayerException;

@WebServlet(name = "filmApi", urlPatterns = "/api/film/*")
public class FilmApiServlet extends ApiServlet {
	private static final long serialVersionUID = 1L;
	private IFilmService filmService;

	public FilmApiServlet() {
	}

	@Override
	public void init() throws ServletException {
		super.init();
		filmService = (IFilmService) getServletContext().getAttribute(IFilmService.class.getName());
		addAction("all", this::all);
		addAction("search", this::search);
	}
	
	private String search(HttpServletRequest req) throws IOException, ServletException {
		return filmService.searchFilmAsJson(req.getParameter("input"));
	}
	
	private String all(HttpServletRequest req) throws IOException, ServletException {
		return filmService.getAllFilmAsJson();
	}
}
