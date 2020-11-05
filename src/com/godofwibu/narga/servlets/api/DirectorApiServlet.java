package com.godofwibu.narga.servlets.api;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "directorApi", urlPatterns = {"/api/director/*"})
public class DirectorApiServlet extends ApiServlet {
	private static final long serialVersionUID = 1L;
       
    public DirectorApiServlet() { }
}
