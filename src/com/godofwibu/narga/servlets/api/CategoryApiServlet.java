package com.godofwibu.narga.servlets.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.godofwibu.narga.services.ICategoryService;

@WebServlet(name = "categoryApi", urlPatterns = { "/api/category/*" })
public class CategoryApiServlet extends ApiServlet {
	private static final long serialVersionUID = -7345744707764843994L;
	private ICategoryService categoryService;

	public CategoryApiServlet() {
	}

	@Override
	public void init() throws ServletException {
		super.init();
		categoryService = getAttribute(ICategoryService.class);
		addAction("all", this::all);
	}

	private String all(HttpServletRequest req) throws IOException, ServletException{
		return categoryService.getAllCategoriesAsJson();
	}
}
