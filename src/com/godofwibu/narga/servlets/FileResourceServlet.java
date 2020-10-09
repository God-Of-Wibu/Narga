package com.godofwibu.narga.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;


@WebServlet(urlPatterns = "/file/*", initParams = {
		@WebInitParam(name = "prefix", value = "files")
})
public class FileResourceServlet extends ResourceServlet {
	private static final long serialVersionUID = 1L;
	private File directory;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		String prefix = "files";
		
		if (config.getInitParameter("prefix") != null) {
			prefix = config.getInitParameter(prefix);
		}
		
		directory = new File(prefix);
	}
	
	@Override
	protected IResource resolveResource(HttpServletRequest req) {
		String name = req.getPathInfo();

		if (name == null || name.isEmpty() || "/".equals(name)) {
			throw new IllegalArgumentException();
		}
		
		name = name.substring(1);
		
		File file = new File(directory, name);
		
		return new IResource() {
			
			@Override
			public String getName() {
				return file.getName();
			}
			
			@Override
			public InputStream getInputStream() throws IOException {
				return new FileInputStream(file);
			}
			
			@Override
			public long getContentLength() {
				return file.length();
			}
		};
	}
	
}
