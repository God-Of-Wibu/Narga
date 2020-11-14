package com.godofwibu.narga.servlets;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.dto.NewIssueFormData;
import com.godofwibu.narga.services.IIssueService;
import com.godofwibu.narga.utils.FormParser;

@WebServlet("/admin/new-issue")
public class NewIssueServlet extends NargaServlet {
	private static final long serialVersionUID = 1L;

	private TemplateEngine templateEngine;
	private IIssueService issueService;
	private FormParser formParser;
	
	@Override
	public void init() throws ServletException {
		super.init();
		templateEngine = getAttribute(TemplateEngine.class);
		issueService = getAttribute(IIssueService.class);
		formParser = getAttribute(FormParser.class);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext context = new WebContext(req, res, getServletContext(), req.getLocale());
		res.setCharacterEncoding("UTF-8");
		templateEngine.process("newIssue", context, res.getWriter());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		NewIssueFormData formData = formParser.getFormObject(req, NewIssueFormData.class);
		
				
	}
}
