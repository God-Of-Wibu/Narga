package com.godofwibu.narga.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;import org.thymeleaf.TemplateEngine;

import com.godofwibu.narga.utils.FormObjectBinder;

public class NargaServlet extends HttpServlet {
	private TemplateEngine templateEngine; 
	private FormObjectBinder formObjectBinder;
	@Override
	public void init() throws ServletException {
		super.init();
		templateEngine = getDepenencyByClassName(TemplateEngine.class);
		formObjectBinder = getDepenencyByClassName(FormObjectBinder.class);
	
	}
	
	
	protected TemplateEngine getTemplateEngine() {
		return templateEngine;
	}
	
	protected <T> T getDepenencyByClassName(Class<T> cls) {
		return cls.cast(getServletContext().getAttribute(cls.getName()));
	}


	protected FormObjectBinder getFormObjectBinder() {
		return formObjectBinder;
	}
	
}
