package com.godofwibu.narga.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;


@WebServlet("/admin/add-film")
public class AddFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TemplateEngine templateEngine;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		templateEngine = (TemplateEngine) getServletContext().getAttribute(TemplateEngine.class.getName());
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		WebContext webContext = new WebContext(req, res, getServletContext(), req.getLocale());
		
		templateEngine.process("addFilm", webContext, res.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
