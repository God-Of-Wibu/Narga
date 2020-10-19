package com.godofwibu.narga.servlets.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.godofwibu.narga.services.IActorService;

@WebServlet(urlPatterns = "/api/actor/*")
public class ActorApiServlet extends ApiServlet {
	
	private static final long serialVersionUID = -5168855078910368683L;
	private IActorService actorService;
	
	public ActorApiServlet() { }
	
	@Override
	public void init() throws ServletException {
		super.init();
		actorService = (IActorService) getServletContext().getAttribute(IActorService.class.getName());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String action = getAction(req);
		switch (action) {
		case "all":
			getAllActors(req, res);
			break;
		case "search":
			searchActors(req, res);
			break;
		default:
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			break;
		}
	}

	private void getAllActors(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(actorService.getAllActorsAsJson());
        res.flushBuffer();
	}

	private void searchActors(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		String input = req.getParameter("input");
		if (input == null) {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		} 
		res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        if (input.equals("")) {
        	res.getWriter().write(actorService.getFirstActorsAsJson(15));
        } else {
        	res.getWriter().write(actorService.searchActorAsJson(input, 15));
        }
        res.flushBuffer();
	}
}
