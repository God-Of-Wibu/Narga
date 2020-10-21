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

import com.godofwibu.narga.entities.Film;
import com.godofwibu.narga.entities.Gender;
import com.godofwibu.narga.repositories.CategoryRepository;
import com.godofwibu.narga.repositories.ICategoryRepository;
import com.godofwibu.narga.repositories.ICountryRepository;
import com.godofwibu.narga.services.CategoryService;
import com.godofwibu.narga.services.ICountryService;
import com.godofwibu.narga.services.IFilmService;
import com.godofwibu.narga.services.ServiceLayerException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

@WebServlet(urlPatterns = { "/admin/add-film", "/admin/get-cats" })
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class AddFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(AddFilmServlet.class);

	private TemplateEngine templateEngine;
	private ICountryService countryService;
	private IFilmService filmService;
	

	@Override
	public void init() throws ServletException {
		super.init();
		templateEngine = (TemplateEngine) getServletContext().getAttribute(TemplateEngine.class.getName());
		filmService = (IFilmService) getServletContext().getAttribute(IFilmService.class.getName());
		countryService = (ICountryService) getServletContext().getAttribute(ICountryService.class.getName());
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext webContext = new WebContext(req, res, getServletContext(), req.getLocale());
		webContext.setVariable("countries", countryService.getAllCountries());
		templateEngine.process("addFilm", webContext, res.getWriter());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		Map<String, String[]> parameters = req.getParameterMap();
		LOGGER.debug("parameter count: " + parameters.size());
		LOGGER.debug(req.getRequestURI());
		String title = parameters.get("title")[0];
		String country = parameters.get("country")[0];
		String director = parameters.get("director")[0];
		String[] categories = parameters.get("categories");
		String[] casting = parameters.get("casting");
		Part poster = req.getPart("poster");
		int runningTime = Integer.parseInt(parameters.get("runningTime")[0]);
		try {
			filmService.addNewFilm(title, poster, country, director, runningTime, categories, casting);
			res.setContentType("text/plain");
			res.getWriter().print("film's data was added to database sucessfully!");
		} catch (ServiceLayerException e) {
			res.setContentType("text/plain");
			res.getWriter().print("something wrong happened: "+ e.getMessage());
			e.printStackTrace();
			LOGGER.error("some thing wrong happened: " + e.getMessage());
		}
 	}
}
