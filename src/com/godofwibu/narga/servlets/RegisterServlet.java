package com.godofwibu.narga.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionIdListener;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.entities.User;
import com.godofwibu.narga.repositories.IUserRepository;
import com.godofwibu.narga.repositories.UserRepository;

@WebServlet(name = "registerServlet", urlPatterns = { "/register" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private TemplateEngine templateEngine;
	private IUserRepository userRepository;

	public RegisterServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext ctx = getServletContext();
		templateEngine = (TemplateEngine) ctx.getAttribute(TemplateEngine.class.getName());
		userRepository = (IUserRepository) ctx.getAttribute(IUserRepository.class.getName());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
		templateEngine.process("register", webContext, response.getWriter());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String[]> params = request.getParameterMap();
		String userId = params.get("userId")[0];
		String password = params.get("password")[0];
		String personalId = params.get("personalId")[0];
		String email = params.get("email")[0];
		String phoneNumber = params.get("phoneNumber")[0];
		String role = "MEMBER";
		register(userId, password, personalId, email,phoneNumber, "", role, request, response);

	}

	private void register(String username, String password, String personalID,String email, String phoneNumber, String name,
			String roles, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User user = new User(username, password, personalID, phoneNumber, email, name, roles);
		if (!isAlreadyExist(username)) {
			userRepository.insert(user);
			WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
			templateEngine.process("registerSuccess", webContext, response.getWriter());
		} else {
			WebContext webContext = new WebContext(request, response, getServletContext(), request.getLocale());
			webContext.setVariable("fail", "User is already exist");
			templateEngine.process("register", webContext, response.getWriter());

		}
	}

	private boolean isAlreadyExist(String username) {
		return userRepository.get(username) != null;
	}
}
