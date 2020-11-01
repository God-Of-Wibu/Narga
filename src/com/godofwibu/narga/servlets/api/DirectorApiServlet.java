package com.godofwibu.narga.servlets.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "directorApi", urlPatterns = {"/api/director/*"})
public class DirectorApiServlet extends ApiServlet {
	private static final long serialVersionUID = 1L;
       
    public DirectorApiServlet() { }
}
