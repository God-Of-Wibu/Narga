package com.godofwibu.narga.servlets;

import javax.servlet.annotation.WebServlet;


@WebServlet("/file/*")
public class FileResourceServlet extends ResourceServlet {
	private static final long serialVersionUID = 1L;
	private FileResourceResolver resolver;
	@Override
	protected IResourceResolver getResourceResolver() {
		if (resolver == null) {
			resolver = new FileResourceResolver();
		}
		return resolver;
	}
}
