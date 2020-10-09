package com.godofwibu.narga.servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet(urlPatterns = "/static/*", initParams = {
		@WebInitParam(name = "prefix", value = "/WEB-INF")
})
public class StaticResourceServlet extends ResourceServlet {
	private static final long serialVersionUID = 1L;

	private String prefix;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		prefix = "/WEB-INF";
		if (config.getInitParameter("prefix") != null) {
			prefix = config.getInitParameter("prefix");
		}
	}
	
	@Override
	protected IResource resolveResource(HttpServletRequest req) {
		String name = req.getPathInfo();

		if (name == null || name.isEmpty() || "/".equals(name)) {
			throw new IllegalArgumentException();
		}
		
		InputStream inputStream = getServletContext().getResourceAsStream(prefix + name);
		
		if (inputStream == null)
			return null;
		
		String tmp = name;
		return new IResource() {
			
			@Override
			public String getName() {
				return tmp;
			}
			
			@Override
			public InputStream getInputStream() throws IOException {
				return inputStream;
			}
			
			@Override
			public long getContentLength() {
				try {
					return inputStream.available();
				} catch (IOException e) {
					e.printStackTrace();
					return -1;
				}
			}
		};
	}
}
