package com.godofwibu.narga.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.entities.User;
import com.godofwibu.narga.services.IAccountService;

@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private TemplateEngine templateEngine;
	private IAccountService accountService;

	public LoginServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext ctx = getServletContext();
		templateEngine = (TemplateEngine) ctx.getAttribute(TemplateEngine.class.getName());
		accountService = (IAccountService) ctx.getAttribute(IAccountService.class.getName());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
		templateEngine.process("login", webContext, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String[]> params = request.getParameterMap();
		String username = params.get("username")[0];
		String password = params.get("password")[0];
		login(request, response, username, password);
	}

	private void login(HttpServletRequest req, HttpServletResponse res, String username, String password) throws ServletException, IOException {
		User user = accountService.getUser(username);
		
		if (user != null && user.getPassword().equals(password)) {
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			res.sendRedirect(req.getContextPath() + "/home");
			
		} else {
			req.setAttribute("status", "username or password is incorrect.");
			doGet(req, res);
		}
	}

}
