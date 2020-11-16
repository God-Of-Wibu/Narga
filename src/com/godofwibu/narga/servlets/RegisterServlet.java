package com.godofwibu.narga.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.entities.Role;
import com.godofwibu.narga.services.IAccountService;
import com.godofwibu.narga.services.UserCreationException;
import com.godofwibu.narga.utils.FormParser;

@WebServlet(name = "registerServlet", urlPatterns = { "/register" })
public class RegisterServlet extends NargaServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterServlet.class);

	private TemplateEngine templateEngine;
	private IAccountService accountService;
	private FormParser formParser;

	@Override
	public void init() throws ServletException {
		super.init();
		templateEngine = getAttribute(TemplateEngine.class);
		accountService = getAttribute(IAccountService.class);
		formParser = getAttribute(FormParser.class);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
		templateEngine.process("register", webContext, response.getWriter());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
	}
}
