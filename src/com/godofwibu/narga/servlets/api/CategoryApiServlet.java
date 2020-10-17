package com.godofwibu.narga.servlets.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.godofwibu.narga.services.ICategoryService;

@WebServlet(name = "categoryApi", urlPatterns = {"/api/category/*"})
public class CategoryApiServlet extends ApiServlet {
	private static final long serialVersionUID = -7345744707764843994L;
	private ICategoryService categoryService;
	
	public CategoryApiServlet() { }
	
	@Override
	public void init() throws ServletException {
		super.init();
		categoryService = (ICategoryService) getServletContext().getAttribute(ICategoryService.class.getName());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String action = getAction(req);
		if ("all".equals(action)) {
			getAllCategories(res);
		} else {
			res.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}
	
	private void getAllCategories(HttpServletResponse res) throws IOException {
		res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().print(categoryService.getAllCategoriesAsJson());
        res.flushBuffer();
	}
}
