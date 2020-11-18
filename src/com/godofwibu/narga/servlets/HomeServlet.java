package com.godofwibu.narga.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.services.IFilmService;

@WebServlet("/home")
public class HomeServlet extends NargaServlet {
	private static final long serialVersionUID = 1L;
	private IFilmService filmService;
	private TemplateEngine templateEngine;
	
    @Override
    public void init() throws ServletException {
    	super.init();
    	filmService = getAttributeByClassName(IFilmService.class);
    	templateEngine = getAttributeByClassName(TemplateEngine.class);
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext webContext = new WebContext(req, res, getServletContext());
		
		webContext.setVariable("films", filmService.getFilmInThisWeek());
		webContext.setVariable("hotFilms", filmService.getHotFilms());
		res.setContentType("text/html; charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		templateEngine.process("home", webContext, res.getWriter());
	}

}
