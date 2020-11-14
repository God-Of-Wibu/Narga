package com.godofwibu.narga.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.repositories.IDirectorRepository;
import com.godofwibu.narga.services.IDirectorService;


@WebServlet(name = "directorDetailServlet", urlPatterns = "/detail/director/*")
public class DirectorDetailServlet extends NargaServlet {
	private static final long serialVersionUID = 1L;
	private IDirectorService directorService;
       

   @Override
   		public void init() throws ServletException {
	   super.init();
	   directorService = getAttribute(IDirectorService.class);
}

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext context = new WebContext(req, res, getServletContext());
		int directorId = Integer.parseInt(req.getPathInfo().substring(1));
		context.setVariable("director", directorService.getDirectorDetail(directorId));
		res.setContentType("text/html; charset=UTF-8");
		res.setCharacterEncoding("UTF-8");
		getTemplateEngine().process("directorDetail", context, res.getWriter());
	}


}
