package com.godofwibu.narga.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.dto.AddFilmFormData;
import com.godofwibu.narga.entities.Film;
import com.godofwibu.narga.entities.Gender;
import com.godofwibu.narga.repositories.CategoryRepository;
import com.godofwibu.narga.repositories.ICategoryRepository;
import com.godofwibu.narga.repositories.ICountryRepository;
import com.godofwibu.narga.services.CategoryService;
import com.godofwibu.narga.services.ICountryService;
import com.godofwibu.narga.services.IFilmService;
import com.godofwibu.narga.services.ServiceLayerException;
import com.godofwibu.narga.utils.RequiredFieldException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

@WebServlet(urlPatterns = { "/admin/add/film"})
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class AddFilmServlet extends NargaServlet {
	private static final long serialVersionUID = 1L;
	private ICountryService countryService;
	private IFilmService filmService;
	

	@Override
	public void init() throws ServletException {
		super.init();
		filmService = getDepenencyByClassName(IFilmService.class);
		countryService = getDepenencyByClassName(ICountryService.class);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext webContext = new WebContext(req, res, getServletContext(), req.getLocale());
		webContext.setVariable("countries", countryService.getAllCountries());
		getTemplateEngine().process("addFilm", webContext, res.getWriter());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		try {	
			AddFilmFormData addFilmFormData = getFormObjectBinder().getFormObject(req, AddFilmFormData.class);
			filmService.addNewFilm(addFilmFormData);
			res.setContentType("text/plain");
			res.getWriter().print("successfully!");
		} catch (ServiceLayerException | RequiredFieldException e) {
			res.setContentType("text/plain");
			res.getWriter().print(e.getMessage());
		}
 	}
}
