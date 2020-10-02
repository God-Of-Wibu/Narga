package com.godofwibu.narga.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.servlets.DefaultServlet;

@WebServlet(name = "staticServlet", urlPatterns = "/static/*")
public class StaticServlet extends DefaultServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected String getRelativePath(HttpServletRequest req) {
		return "/static" + super.getRelativePath(req);
	}

}
