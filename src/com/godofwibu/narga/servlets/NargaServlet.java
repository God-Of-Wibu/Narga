package com.godofwibu.narga.servlets;

import javax.servlet.http.HttpServlet;

public class NargaServlet extends HttpServlet {
	
	protected <T> T getAttributeByClassName(Class<T> cls) {
		return getAttribute(cls.getName(), cls);
	}
	
	protected <T> T getAttribute(String name, Class<T> cls) {
		return cls.cast(getServletContext().getAttribute(name));
	}
}
