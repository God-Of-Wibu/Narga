package com.godofwibu.narga.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.formdata.NewIssueFormData;
import com.godofwibu.narga.services.IIssueService;
import com.godofwibu.narga.services.exception.ServiceLayerException;
import com.godofwibu.narga.utils.FormParser;

@WebServlet("/admin/new-issue")
public class NewIssueServlet extends NargaServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOOGER = org.slf4j.LoggerFactory.getLogger(NewIssueServlet.class);

	private TemplateEngine templateEngine;
	private IIssueService issueService;
	private FormParser formParser;
	
	@Override
	public void init() throws ServletException {
		super.init();
		templateEngine = getAttributeByClassName(TemplateEngine.class);
		issueService = getAttributeByClassName(IIssueService.class);
		formParser = getAttributeByClassName(FormParser.class);
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
		NewIssueFormData formData = formParser.parse(req, NewIssueFormData.class);
		res.setCharacterEncoding("UTF-8");	
		try {
			issueService.newIssues(formData);
			res.getWriter().write("dữ liệu đã được ghi");
		} catch (ServiceLayerException e) {
			LOOGER.error("Error", e);
			res.getWriter().write("error: " + e.getMessage());
		}
	}
}
