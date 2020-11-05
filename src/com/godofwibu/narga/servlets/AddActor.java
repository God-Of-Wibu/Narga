package com.godofwibu.narga.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.dto.AddActorFormData;
import com.godofwibu.narga.entities.Gender;
import com.godofwibu.narga.services.IActorService;
import com.godofwibu.narga.services.ICountryService;

@WebServlet(urlPatterns = "/admin/add/actor")
@MultipartConfig(location = "/tmp", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024
		* 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class AddActor extends NargaServlet {
	private static final long serialVersionUID = 1L;
	private ICountryService countryService;
	private IActorService actorService;

	public AddActor() {
	}

	@Override
	public void init() throws ServletException {
		super.init();
		countryService = getAttribute(ICountryService.class);
		actorService = getAttribute(IActorService.class);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext context = new WebContext(req, res, getServletContext(), req.getLocale());
		req.setCharacterEncoding("UTF-8");
		AddActorFormData formData = getFormParser().getFormObject(req, AddActorFormData.class);
		actorService.addNewActor(formData);
		context.setVariable("status", formData.getName() + " was added to database");
		context.setVariable("genderValues", Gender.values());
		context.setVariable("defaultGender", formData.getGender());
		context.setVariable("countries", countryService.getAllCountries());

		getTemplateEngine().process("addActor", context, res.getWriter());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext context = new WebContext(req, res, getServletContext(), req.getLocale());
		context.setVariable("genderValues", Gender.values());
		context.setVariable("defaultGender", Gender.MALE);
		context.setVariable("countries", countryService.getAllCountries());
		res.setCharacterEncoding("UTF-8");
		getTemplateEngine().process("addActor", context, res.getWriter());
	}

}
