package com.godofwibu.narga.servlets.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, IHandler> handlerMap;

	public ApiServlet() {
		handlerMap = new HashMap<String, IHandler>();
	}

	protected String getAction(HttpServletRequest req) {
		String pathInfo = req.getPathInfo();
		return pathInfo != null ? pathInfo.substring(1) : "";
	}

	private void writeJson(HttpServletResponse res, String json) throws IOException {
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().write(json);
		res.flushBuffer();
	}

	protected void addAction(String action, IHandler handle) {
		handlerMap.putIfAbsent(action, handle);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = getAction(req);
		IHandler handler = resolveHandlerForAction(action);
		writeJson(res, handler.execute(req));
	}
	
	private IHandler resolveHandlerForAction(String action) {
		return handlerMap.get(action);
	}
	
	protected <T> T getAttribute(Class<T> cls) {
		return getAttribute(cls.getName(), cls);
	}
	
	protected <T> T getAttribute(String name, Class<T> cls) {
		return cls.cast(getServletContext().getAttribute(name));
	}

}
