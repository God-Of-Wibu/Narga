package com.godofwibu.narga.servlets.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.godofwibu.narga.services.IFilmService;

@WebServlet(name = "filmApi", urlPatterns = "/api/film/*")
public class FilmApiServlet extends ApiServlet {
	private static final long serialVersionUID = 1L;
    private IFilmService filmService;
    public FilmApiServlet() { }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	filmService = (IFilmService) getServletContext().getAttribute(IFilmService.class.getName());
    }

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = getAction(req);
		if ("all".equals(action)) {
			getAllFilm(res);
		} else if("search".equals(action)){
			searchFilm(res, req.getParameter("input"));
		}else {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	private void getAllFilm(HttpServletResponse res) throws IOException {
		res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().print(filmService.getAllFilmAsJson());
        res.flushBuffer();
	}
	
	private void searchFilm(HttpServletResponse res, String input) throws IOException {
		if (input == null || input.equals("")) {
			getAllFilm(res);
		} else {
			res.setContentType("application/json");
	        res.setCharacterEncoding("UTF-8");
	        res.getWriter().print(filmService.searchFilmAsJson(input));
	        res.flushBuffer();
		}
	}
}
