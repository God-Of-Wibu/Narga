package com.godofwibu.narga.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.services.IFilmService;

@WebServlet(name = "filmDetailServlet", urlPatterns = "/detail/film/*")
public class FilmDetailServlet extends NargaServlet {
	private static final long serialVersionUID = 5692077327278092262L;
	
	private IFilmService filmService;
	private TemplateEngine templateEngine;
	
	@Override
	public void init() throws ServletException {
		super.init();
		filmService = getAttributeByClassName(IFilmService.class);
		templateEngine = getAttributeByClassName(TemplateEngine.class);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext webContext = new WebContext(req, res, getServletContext(), req.getLocale());
		
		int filmId = Integer.parseInt(req.getPathInfo().substring(1));
		
		webContext.setVariable("film", filmService.getFilmDetail(filmId));
		res.setContentType("text/html; charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		templateEngine.process("filmDetail", webContext, res.getWriter());
	}
}
