package com.godofwibu.narga.servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
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
import com.godofwibu.narga.services.CategoryService;
import com.godofwibu.narga.services.IFilmService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

@WebServlet(urlPatterns = { "/admin/add-film", "/admin/get-cats" })
public class AddFilmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(AddFilmServlet.class);

	private TemplateEngine templateEngine;
	private ICategoryRepository categoryRepository;
	private CategoryService helper;
	private IFilmService filmService;
	

	@Override
	public void init() throws ServletException {
		super.init();

		templateEngine = (TemplateEngine) getServletContext().getAttribute(TemplateEngine.class.getName());
		categoryRepository = (ICategoryRepository) getServletContext().getAttribute(ICategoryRepository.class.getName());
		filmService = (IFilmService) getServletContext().getAttribute(IFilmService.class.getName());
		helper = new CategoryService(categoryRepository);
		
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		if (req.getRequestURI().equals(req.getContextPath() + "/admin/get-cats")) {
			doGetCat(req, res);
		} else if (req.getRequestURI().equals(req.getContextPath() + "/admin/add-film")) {
			doGetForm(req, res);
		}
	}

	private void doGetForm(HttpServletRequest req, HttpServletResponse res) throws IOException {
		WebContext webContext = new WebContext(req, res, getServletContext(), req.getLocale());
		templateEngine.process("addFilm", webContext, res.getWriter());
	}

	private void doGetCat(HttpServletRequest req, HttpServletResponse res) throws IOException {
		res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(helper.getAllCategoriesAsJson());
        res.getWriter().flush();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		Map<String, String[]> parameters = req.getParameterMap();
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
		} catch (Exception e) {
			res.setContentType("text/plain");
			res.getWriter().print("something wrong happened: "+ e.getMessage());
		}
 	}
}
