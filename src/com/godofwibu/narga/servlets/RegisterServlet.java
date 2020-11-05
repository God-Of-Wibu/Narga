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

@WebServlet(name = "registerServlet", urlPatterns = { "/register" })
public class RegisterServlet extends NargaServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(RegisterServlet.class);

	private TemplateEngine templateEngine;
	private IAccountService accountService;

	public RegisterServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		templateEngine = getAttribute(TemplateEngine.class);
		accountService = getAttribute(IAccountService.class);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
		templateEngine.process("register", webContext, response.getWriter());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		Map<String, String[]> params = req.getParameterMap();
		String userId = params.get("userId")[0];
		String password = params.get("password")[0];
		String confirmPassword = params.get("confirmPassword")[0];
		String personalId = params.get("personalId")[0];
		String name = params.get("name")[0];
		Role role = Role.MEMBER;
		
		try {
			accountService.registerNewUser(userId, password, confirmPassword, role, name, personalId);
			req.getSession().setAttribute("user", accountService.loadUserById(userId));
			req.getRequestDispatcher("/index").forward(req, res);
			LOGGER.error("registered new user: {}", userId);
		} catch (UserCreationException e) {
			LOGGER.error("Fail to register new user: {}", e.getMessage());
			WebContext webContext = new WebContext(req, res, getServletContext(), req.getLocale());
			webContext.setVariable("message", e.getMessage());
			templateEngine.process("register", webContext, res.getWriter());
		}
	}
}
