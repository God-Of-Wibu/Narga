package com.godofwibu.narga.servlets.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import com.godofwibu.narga.services.IActorService;

@WebServlet(urlPatterns = "/api/actor/*")
public class ActorApiServlet extends ApiServlet {

	private static final long serialVersionUID = -5168855078910368683L;
	private IActorService actorService;

	public ActorApiServlet() {
	}

	@Override
	public void init() throws ServletException {
		super.init();
		actorService = (IActorService) getServletContext().getAttribute(IActorService.class.getName());
		addAction("all", this::all);
		addAction("search", this::search);
	}

	private String all(HttpServletRequest req) throws IOException, ServletException {
		return actorService.getAllActorsAsJson();
	}
	
	private String search(HttpServletRequest req) throws IOException, ServletException {
		return actorService.searchActorAsJson(req.getParameter("input"), 15);
	}
}
