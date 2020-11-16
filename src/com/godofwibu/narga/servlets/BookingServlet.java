package com.godofwibu.narga.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.entities.Film;
import com.godofwibu.narga.formdata.BookingFormData;
import com.godofwibu.narga.services.FilmService;
import com.godofwibu.narga.services.IBookingService;
import com.godofwibu.narga.services.IFilmService;
import com.godofwibu.narga.utils.FormParser;

// /book/{filmid}
@WebServlet("/book")
public class BookingServlet extends NargaServlet {
	
	private TemplateEngine templateEngine;
	private IFilmService filmService;
	private IBookingService bookingService;
	private FormParser formParser;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		templateEngine = getAttribute(TemplateEngine.class);
		filmService = getAttribute(IFilmService.class);
		formParser = getAttribute(FormParser.class);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext context = new WebContext(req, res, getServletContext());
		int filmId;
		try {
			filmId = Integer.parseInt(req.getParameter("filmId"));
		} catch (Exception e) {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return ;
		}
		res.setCharacterEncoding("UTF-8");
		context.setVariable("dayList", getDaysOfCurrentWeek());
		
		Film film = filmService.getFilm(filmId);
		
		if (film == null) {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		context.setVariable("filmId", filmId);
		context.setVariable("filmTitle", film.getTitle());
		context.setVariable("filmPoster", film.getPoster().getUrl());
		templateEngine.process("booking", context, res.getWriter());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		bookingService.book(formParser.getFormObject(req, BookingFormData.class), null);
		res.getWriter().println("Dat ve thanh cong");
	}
	
	private List<Date> getDaysOfCurrentWeek() {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		
		List<Date> dates = new ArrayList<Date>();
		dates.add(cal.getTime());
		cal.add(Calendar.DAY_OF_WEEK, 1);
		dates.add(cal.getTime());
		cal.add(Calendar.DAY_OF_WEEK, 1);
		dates.add(cal.getTime());
		cal.add(Calendar.DAY_OF_WEEK, 1);
		dates.add(cal.getTime());
		cal.add(Calendar.DAY_OF_WEEK, 1);
		dates.add(cal.getTime());
		cal.add(Calendar.DAY_OF_WEEK, 1);
		dates.add(cal.getTime());
		cal.add(Calendar.DAY_OF_WEEK, 1);
		dates.add(cal.getTime());
		return dates;
	}
}
