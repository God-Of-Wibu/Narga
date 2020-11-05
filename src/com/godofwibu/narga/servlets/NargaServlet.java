package com.godofwibu.narga.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;import org.thymeleaf.TemplateEngine;

import com.godofwibu.narga.utils.FormParser;

public class NargaServlet extends HttpServlet {
	private TemplateEngine templateEngine; 
	private FormParser formParser;
	@Override
	public void init() throws ServletException {
		super.init();
		templateEngine = getAttribute(TemplateEngine.class);
		formParser = getAttribute(FormParser.class);
	
	}
	
	
	protected TemplateEngine getTemplateEngine() {
		return templateEngine;
	}
	
	protected <T> T getAttribute(Class<T> cls) {
		return getAttribute(cls.getName(), cls);
	}
	
	protected <T> T getAttribute(String name, Class<T> cls) {
		return cls.cast(getServletContext().getAttribute(name));
	}


	protected FormParser getFormParser() {
		return formParser;
	}
	
}
