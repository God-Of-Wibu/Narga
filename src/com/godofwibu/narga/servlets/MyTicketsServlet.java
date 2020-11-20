package com.godofwibu.narga.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.dto.TicketData;
import com.godofwibu.narga.entities.User;
import com.godofwibu.narga.services.IBookingService;

@WebServlet("/my-tickets")
public class MyTicketsServlet extends NargaServlet {
	private static final long serialVersionUID = 5540226601605650621L;
	
	private TemplateEngine templateEngine;
	private IBookingService bookingService;
	
	@Override
	public void init() throws ServletException {
		super.init();
		templateEngine = getAttributeByClassName(TemplateEngine.class);
		bookingService = getAttributeByClassName(IBookingService.class);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext context = new WebContext(req, res, getServletContext());
		
		List<TicketData> list = bookingService.getAllBookedTickets((User) req.getSession().getAttribute("user"));
		System.out.println(list);
		
		context.setVariable("tickets", bookingService.getAllBookedTickets((User) req.getSession().getAttribute("user")));
		
		res.setCharacterEncoding("UTF-8");
		templateEngine.process("myTickets", context, res.getWriter());
	}
}
