package com.godofwibu.narga.servlets;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;


@WebServlet(name = "registerServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TemplateEngine templateEngine;
       
    public RegisterServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	ServletContext ctx = getServletContext();
		templateEngine = (TemplateEngine) ctx.getAttribute(TemplateEngine.class.getName());
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
		templateEngine.process("register", webContext, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
