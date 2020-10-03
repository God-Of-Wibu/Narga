package com.godofwibu.narga.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ResourceServingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final int BUFFER_SIZE = 1024 * 100;
       
	private final String CONTENT_DISPOSITION_HEADER_FMT = "inline;filename=\"%s\"";
   
    public ResourceServingServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();	
    }

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		IResource resource = null;
		
		String name = req.getPathInfo();
		
		if (name == null || name.isEmpty() || "/".equals(name)) {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		name = name.substring(1);
		
		resource = getResourceResolver().getResource(name);
		
		if (resource == null)
		{
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		setHeader(res, resource);
		writeContent(res, resource);		
	}
	
	private void setHeader(HttpServletResponse res, IResource resource) {
		res.setHeader("Content-Type", getServletContext().getMimeType(resource.getName()));
		res.setHeader("Content-Disposition", String.format(CONTENT_DISPOSITION_HEADER_FMT, resource.getName()));
		res.setHeader("Context-Length", String.valueOf(resource.getContentLenght()));
	}
	
	private void writeContent(HttpServletResponse res, IResource resource) throws IOException {
		try (OutputStream outStream = res.getOutputStream(); InputStream inStream = resource.getInputStream()) {
			byte[] buffer = new byte[BUFFER_SIZE];
			int byteRead = -1;
			long length = 0;
			while ((byteRead = inStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
				outStream.write(buffer, 0, byteRead);
				length += byteRead;
			}
			if (resource.getContentLenght() != -1 && !res.isCommitted()) {
				res.setHeader("Context-Length", String.valueOf(length));
			}
		}
	}
	
	protected abstract IResourceResolver getResourceResolver();

}
