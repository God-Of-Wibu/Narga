package com.godofwibu.narga.servlets;

import javax.servlet.annotation.WebServlet;

@WebServlet("/static/*")
public class StaticResourceServlet extends ResourceServlet {
	private static final long serialVersionUID = 1L;
	
	StaticResourceResolver resolver;

	@Override
	protected IResourceResolver getResourceResolver() {
		if (resolver == null) {
			resolver = new StaticResourceResolver(getServletContext());
			resolver.setPrefix("/WEB-INF");
		}
		return resolver;
	}
}
