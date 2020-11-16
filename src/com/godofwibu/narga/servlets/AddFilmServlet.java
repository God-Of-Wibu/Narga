package com.godofwibu.narga.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.formdata.AddFilmFormData;
import com.godofwibu.narga.services.ICountryService;
import com.godofwibu.narga.services.IFilmService;
import com.godofwibu.narga.services.ServiceLayerException;
import com.godofwibu.narga.utils.NoSuchParameterException;

@WebServlet(urlPatterns = { "/admin/add/film"})
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class AddFilmServlet extends NargaServlet {
	private static final long serialVersionUID = 1L;
	private ICountryService countryService;
	private IFilmService filmService;
	

	@Override
	public void init() throws ServletException {
		super.init();
		filmService = getAttribute(IFilmService.class);
		countryService = getAttribute(ICountryService.class);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext webContext = new WebContext(req, res, getServletContext(), req.getLocale());
		webContext.setVariable("countries", countryService.getAllCountries());
		getTemplateEngine().process("addFilm", webContext, res.getWriter());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		try {	
			AddFilmFormData addFilmFormData = getFormParser().getFormObject(req, AddFilmFormData.class);
			filmService.addNewFilm(addFilmFormData);
			res.setContentType("text/plain");
			res.getWriter().print("successfully!");
		} catch (ServiceLayerException e) {
			res.setContentType("text/plain");
			res.getWriter().print(e.getMessage());
		}
 	}
}
