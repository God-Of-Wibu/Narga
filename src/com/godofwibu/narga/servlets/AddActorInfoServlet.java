package com.godofwibu.narga.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.godofwibu.narga.entities.Actor;
import com.godofwibu.narga.entities.Gender;
import com.godofwibu.narga.repositories.IActorRepository;
import com.godofwibu.narga.repositories.ICountryRepository;

@WebServlet("/admin/add-actor-info")
public class AddActorInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private IActorRepository actorRepository;
    private ICountryRepository countryRepository;
    private TemplateEngine templateEngine;
    public AddActorInfoServlet() {
        super();
    }
    
    @Override
    public void init() throws ServletException {
    	super.init();
    	ServletContext ctx = getServletContext();
    	actorRepository = (IActorRepository) ctx.getAttribute(IActorRepository.class.getName());
    	templateEngine = (TemplateEngine) ctx.getAttribute(TemplateEngine.class.getName());
    	countryRepository = (ICountryRepository) ctx.getAttribute(ICountryRepository.class.getName());
    }

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Map<String, String[]> params = req.getParameterMap();
		Gender gender = Gender.MALE.name().equals(params.get("gender")[0]) ? Gender.MALE : Gender.FEMALE;
		String countryId = params.get("country")[0];
		Actor actor = new Actor();
		actor.setName(params.get("name")[0]);
		actor.setAge(Integer.parseInt(params.get("age")[0]));
		actor.setGender(gender);
		actor.setCountry(countryRepository.findById(countryId));
		addActorInfo(req, res, actor);
	}
	
	private void addActorInfo(HttpServletRequest req, HttpServletResponse res, Actor actor) throws IOException {
		
		WebContext context = new WebContext(req, res, getServletContext(), req.getLocale());
		context.setVariable("actor", actor);
		context.setVariable("genderValues", Gender.values());
		context.setVariable("defaultGender", Gender.MALE);
		context.setVariable("countries", countryRepository.findAll());
		
		if (actorRepository.insert(actor) != null) {
			context.setVariable("status", "success");
		} else {
			context.setVariable("status", "fail");
		}
		templateEngine.process("addActorInfo", context, res.getWriter());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		WebContext context = new WebContext(req, res, getServletContext(), req.getLocale());
		context.setVariable("genderValues", Gender.values());
		context.setVariable("defaultGender", Gender.MALE);
		context.setVariable("countries", countryRepository.findAll());
		templateEngine.process("addActorInfo", context, res.getWriter());
	}

}
