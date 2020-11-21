package com.godofwibu.narga.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@WebServlet("/admin")
public class AdminDashboardServlet extends NargaServlet {
	private TemplateEngine templateEngine;
	@Override
	public void init() throws ServletException {
		super.init();
		templateEngine = getAttributeByClassName(TemplateEngine.class);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setCharacterEncoding("UTF-8");
		
		WebContext context = new WebContext(req, res, getServletContext());
		templateEngine.process("adminDashboard", context, res.getWriter());
	}
}
