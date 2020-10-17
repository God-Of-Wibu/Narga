package com.godofwibu.narga.servlets.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ApiServlet
 */
@WebServlet("/ApiServlet")
public class ApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ApiServlet() { }
    
    protected String getAction(HttpServletRequest req) {
		String pathInfo = req.getPathInfo();
		return pathInfo != null ? pathInfo.substring(1) : "";
	}

}
