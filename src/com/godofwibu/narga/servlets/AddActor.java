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

import com.godofwibu.narga.dto.AddFilmFormData;
import com.godofwibu.narga.dto.AddActorFormData;
import com.godofwibu.narga.entities.Gender;
import com.godofwibu.narga.services.IActorService;
import com.godofwibu.narga.services.ICountryService;
import com.godofwibu.narga.services.IDirectorService;
import com.godofwibu.narga.services.ServiceLayerException;
import com.godofwibu.narga.utils.RequiredFieldException;

import static com.godofwibu.narga.servlets.ParameterUtils.*;

@WebServlet(urlPatterns =  "/admin/add/actor")
@MultipartConfig(location = "/tmp", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024
		* 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class AddActor extends NargaServlet {
	private static final long serialVersionUID = 1L;
	private TemplateEngine templateEngine;
	private ICountryService countryService;
	private IActorService actorService;
	private IDirectorService directorService;

	public AddActor() {
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
		

		WebContext context = new WebContext(req, res, getServletContext(), req.getLocale());
		
		try {
			AddActorFormData formData = getFormObjectBinder().getFormObject(req, AddActorFormData.class);
			actorService.addNewActor(formData);
			context.setVariable("status", formData.getName() + " was added to database");
			context.setVariable("genderValues", Gender.values());
			context.setVariable("defaultGender", formData.getGender());
			context.setVariable("countries", countryService.getAllCountries());
		} catch (RequiredFieldException e) {
			context.setVariable("status", "you must fill required field: " + e.getFieldName());
		}
		
		templateEngine.process("addActor", context, res.getWriter());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		WebContext context = new WebContext(req, res, getServletContext(), req.getLocale());
		context.setVariable("genderValues", Gender.values());
		context.setVariable("defaultGender", Gender.MALE);
		context.setVariable("countries", countryService.getAllCountries());
		templateEngine.process("addActor", context, res.getWriter());
	}


}
