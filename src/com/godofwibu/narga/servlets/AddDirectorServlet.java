package com.godofwibu.narga.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.entities.Gender;
import com.godofwibu.narga.formdata.AddActorFormData;
import com.godofwibu.narga.formdata.AddDirectorFormData;
import com.godofwibu.narga.services.IActorService;
import com.godofwibu.narga.services.ICountryService;
import com.godofwibu.narga.services.IDirectorService;
import com.godofwibu.narga.utils.FormParser;


@WebServlet("/admin/add/director")
@MultipartConfig(location = "/tmp", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024
* 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class AddDirectorServlet extends NargaServlet{
	private static final long serialVersionUID = 1L;
	private ICountryService countryService;
	private IDirectorService directorService;
	private TemplateEngine templateEngine;
	private FormParser formParser;
       
 
   @Override
   public void init() throws ServletException {
	   super.init();
		countryService = getAttributeByClassName(ICountryService.class);
		directorService = getAttributeByClassName(IDirectorService.class);
		templateEngine = getAttributeByClassName(TemplateEngine.class);
		formParser = getAttributeByClassName(FormParser.class);
   }
	
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext context = new WebContext(req, res, getServletContext(), req.getLocale());
		req.setCharacterEncoding("UTF-8");
		AddDirectorFormData formData = formParser.parse(req, AddDirectorFormData.class);
		directorService.addNewDirector(formData);
		context.setVariable("status", formData.getName() + " was added to database");
		context.setVariable("genderValues", Gender.values());
		context.setVariable("defaultGender", formData.getGender());
		context.setVariable("countries", countryService.getAllCountries());

		templateEngine.process("addDirector", context, res.getWriter());
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext context = new WebContext(req, res, getServletContext(), req.getLocale());
		context.setVariable("genderValues", Gender.values());
		context.setVariable("defaultGender", Gender.MALE);
		context.setVariable("countries", countryService.getAllCountries());
		res.setCharacterEncoding("UTF-8");
		templateEngine.process("addDirector", context, res.getWriter());
	}

}
