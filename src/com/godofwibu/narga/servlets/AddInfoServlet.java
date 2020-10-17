package com.godofwibu.narga.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.entities.Gender;
import com.godofwibu.narga.services.IActorService;
import com.godofwibu.narga.services.ICountryService;
import com.godofwibu.narga.services.IDirectorService;

import static com.godofwibu.narga.servlets.ParameterUtils.*;

@WebServlet(urlPatterns = { "/admin/add-info/*" })
@MultipartConfig(location = "/tmp", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024
		* 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class AddInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	private ICountryService countryService;
	private IActorService actorService;
	private IDirectorService directorService;

	public AddInfoServlet() {
	}

	@Override
	public void init() throws ServletException {
		super.init();
		ServletContext ctx = getServletContext();

		templateEngine = (TemplateEngine) ctx.getAttribute(TemplateEngine.class.getName());
		countryService = (ICountryService) ctx.getAttribute(ICountryService.class.getName());
		actorService = (IActorService) ctx.getAttribute(IActorService.class.getName());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Map<String, String[]> params = req.getParameterMap();
		String name = getParamAsString(params, "name");
		Gender gender = getParamAsGender(params, "gender");
		String countryName = getParamAsString(params, "country");
		int age = getParamAsInteger(params, "age");
		Part avatarPart = req.getPart("avatar");
		
		WebContext context = new WebContext(req, res, getServletContext(), req.getLocale());
		String typeToBeAdded = getTypeToBeAdded(req);
		try {
			switch (typeToBeAdded) {
			case "actor":
				actorService.addNewActor(name, age, gender, countryName, avatarPart);
				break;
			case "director":
				directorService.addNewDirector(name, age, gender, countryName, avatarPart);
				break;
			default:
				res.sendError(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			
			context.setVariable("status", name +" was added to database");
			
			
		} catch (Exception e) {
			context.setVariable("status", "something wrong happened: " + e.getMessage());
		}
		context.setVariable("genderValues", Gender.values());
		context.setVariable("defaultGender", gender);
		context.setVariable("countries", countryService.getAllCoutries());
		context.setVariable("submitLink", req.getRequestURI());
		templateEngine.process("addInfo", context, res.getWriter());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		String typeToBeAdded = getTypeToBeAdded(req);
		if (!typeToBeAdded.equals("actor") && !typeToBeAdded.equals("director")) {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		WebContext context = new WebContext(req, res, getServletContext(), req.getLocale());
		context.setVariable("genderValues", Gender.values());
		context.setVariable("defaultGender", Gender.MALE);
		context.setVariable("countries", countryService.getAllCoutries());
		context.setVariable("submitLink", req.getRequestURI());
		templateEngine.process("addInfo", context, res.getWriter());
	}

	private String getTypeToBeAdded(HttpServletRequest req) {
		String pathInfo = req.getPathInfo();
		return pathInfo != null ? pathInfo.substring(1) : "";
	}

}
