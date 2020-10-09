package com.godofwibu.narga;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.godofwibu.narga.entities.Category;
import com.godofwibu.narga.repositories.CategoryRepository;
import com.godofwibu.narga.repositories.ICategoryRepository;
import com.godofwibu.narga.repositories.IUserRepository;
import com.godofwibu.narga.repositories.UserRepository;

@WebListener
public class Initializer implements ServletContextListener {

	private static final String HIBERNATE_CFG_PATH = "hibernate.cfg.xml";
	private SessionFactory sessionFactory;
	private TemplateEngine templateEngine;
	private IUserRepository userRepository;
	private ICategoryRepository categoryRepository;
	
	private final static Logger LOGGER = Logger.getLogger(Initializer.class);

	public Initializer() { }

	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.debug("Initializing...");
		ServletContext ctx = sce.getServletContext();

		ctx.setAttribute(SessionFactory.class.getName(), getSessionFactory());
		ctx.setAttribute(TemplateEngine.class.getName(), getTemplateEngine(ctx));
		ctx.setAttribute(IUserRepository.class.getName(), getUserRepository());
		ctx.setAttribute(ICategoryRepository.class.getName(), getCategoryRepository());
		
		insertSomeUsers();
		insertCategories();
		LOGGER.debug("Initializing done!");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		if (sessionFactory != null)
			sessionFactory.close();
	}

	private SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
					.configure(HIBERNATE_CFG_PATH)
					.build();
			MetadataSources metadataSources = new MetadataSources(registry);

			sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
		}
		return sessionFactory;
	}

	private ITemplateResolver getTemplateResolver(final ServletContext servletContext) {
		ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);

		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
		templateResolver.setCacheable(true);
		return templateResolver;
	}

	private TemplateEngine getTemplateEngine(final ServletContext servletContext) {
		if (templateEngine == null) {
			templateEngine = new TemplateEngine();
			templateEngine.setTemplateResolver(getTemplateResolver(servletContext));
		}
		return templateEngine;
	}
	
	private IUserRepository getUserRepository() {
		if (userRepository == null) {
			userRepository = new UserRepository(getSessionFactory());
		}
		return userRepository;
	}
	
	private void insertSomeUsers() {
		Session session =  getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		//session.save(new User("user_001", "123", "241813141", "0987205513", null,"Ngo Thoi Trung", "MEMBER"));
		//session.save(new User("user_002", "123", "241813141", "0987205513", null,"Ngo Thoi Trung", "MEMBER"));
		//session.save(new User("user_003", "123", "241813141", "0987205513", null,"Ngo Thoi Trung", "MEMBER"));
		
		transaction.commit();
	}
	
	private void insertCategories() {
		ICategoryRepository repo = getCategoryRepository();
		repo.insert(new Category("CMD", "hài"));
		repo.insert(new Category("ACT", "hành động"));
	}
	
	private ICategoryRepository getCategoryRepository() {
		if (categoryRepository == null) {
			categoryRepository = new CategoryRepository(getSessionFactory());
		}
		return categoryRepository;
	}
}
