package com.godofwibu.narga.servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import lombok.Getter;
import lombok.Setter;

public class StaticResourceResolver implements IResourceResolver {
	
	@Setter
	@Getter
	private String prefix;
	
	@Getter
	@Setter
	private ServletContext servletContext;
	
	public StaticResourceResolver(ServletContext servletContext) {
		super();
		this.servletContext = servletContext;
		this.prefix = "/WEB-INF";
	}

	@Override
	public IResource getResource(String name){

		String path = prefix + "/" + name;
		InputStream inStream = servletContext.getResourceAsStream(path);
		if (inStream == null) 
			return null;
		
		return new IResource() {
			@Override
			public String getName() {
				return name;
			}
			
			@Override
			public InputStream getInputStream() {
				return inStream;
			}
			
			@Override
			public long getContentLength() {			
				try {
					return inStream.available();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return -1;
			}
		};
	}
	

}
