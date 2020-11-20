package com.godofwibu.narga.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.entities.Actor;
import com.godofwibu.narga.services.IActorService;

@WebServlet(name = "actorDetailServlet", urlPatterns = "/detail/actor/*")
public class ActorDetailServlet extends NargaServlet {
	private static final long serialVersionUID = 1L;
	private IActorService actorService;
	private TemplateEngine templateEngine;
	
	
	@Override
 	public void init() throws ServletException {
		super.init();
		actorService = getAttributeByClassName(IActorService.class);
		templateEngine = getAttributeByClassName(TemplateEngine.class);
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext context = new WebContext(req, res, getServletContext());
		int actorId = Integer.parseInt(req.getPathInfo().substring(1));
		
		context.setVariable("actor", actorService.getActorDetail(actorId));
		res.setContentType("text/html; charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		templateEngine.process("actorDetail", context, res.getWriter());
	}

}
