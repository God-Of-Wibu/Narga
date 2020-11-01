package com.godofwibu.narga;

import java.io.IOException;

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
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import com.godofwibu.narga.entities.Actor;
import com.godofwibu.narga.entities.Category;
import com.godofwibu.narga.entities.Country;
import com.godofwibu.narga.entities.Director;
import com.godofwibu.narga.entities.Film;
import com.godofwibu.narga.entities.Gender;
import com.godofwibu.narga.entities.ImageData;
import com.godofwibu.narga.repositories.ActorRepository;
import com.godofwibu.narga.repositories.CategoryRepository;
import com.godofwibu.narga.repositories.CountryRepository;
import com.godofwibu.narga.repositories.FilmRepository;
import com.godofwibu.narga.repositories.IActorRepository;
import com.godofwibu.narga.repositories.ICategoryRepository;
import com.godofwibu.narga.repositories.ICountryRepository;
import com.godofwibu.narga.repositories.IFilmRepository;
import com.godofwibu.narga.repositories.IImageDataRepository;
import com.godofwibu.narga.repositories.IUserRepository;
import com.godofwibu.narga.repositories.ImageDataRepository;
import com.godofwibu.narga.repositories.UserRepository;
import com.godofwibu.narga.services.AccountService;
import com.godofwibu.narga.services.ActorService;
import com.godofwibu.narga.services.CategoryService;
import com.godofwibu.narga.services.CountryService;
import com.godofwibu.narga.services.FilmService;
import com.godofwibu.narga.services.IAccountService;
import com.godofwibu.narga.services.IActorService;
import com.godofwibu.narga.services.ICategoryService;
import com.godofwibu.narga.services.ICountryService;
import com.godofwibu.narga.services.IFilmService;
import com.godofwibu.narga.services.IImageStorageService;
import com.godofwibu.narga.services.ImageStorageService;
import com.godofwibu.narga.utils.FormObjectBinder;
import com.godofwibu.narga.utils.GenderConverter;
import com.godofwibu.narga.utils.HibernateTransactionTemplate;
import com.godofwibu.narga.utils.ITransactionTemplate;
import com.godofwibu.narga.utils.IntegerArrayConverter;
import com.godofwibu.narga.utils.IntegerConverter;
import com.godofwibu.narga.utils.StringArrayConverter;
import com.godofwibu.narga.utils.StringConverter;

@WebListener
public class Initializer implements ServletContextListener {

	private static final String HIBERNATE_CFG_PATH = "hibernate.cfg.xml";
	private final static Logger LOGGER = Logger.getLogger(Initializer.class);
	
	public final static String FILE_DIRECTORY = "file";
	public final static String STATIC_RESOURCE_PREFIX = "/static";
	public final static String FILE_RESOURCE_PREFIX = "/file";
	
	private SessionFactory sessionFactory;
	private TemplateEngine templateEngine;
	private IUserRepository userRepository;
	private ICategoryRepository categoryRepository;
	private IActorRepository actorRepository;
	private ICountryRepository countryRepository;
	private IAccountService accountService;
	private IImageStorageService imageStorageService;
	private IImageDataRepository imageDataRepository;
	private IFilmService filmService;
	private IFilmRepository filmRepository;
	private String contextPath;
	private ICategoryService categoryService;
	private ICountryService countryService;
	private IActorService actorService;
	private ITransactionTemplate transactionTemplate;
	private FormObjectBinder formObjectBinder;
	
	

	public Initializer() { }

	public void contextInitialized(ServletContextEvent sce) {
		ServletContext ctx = sce.getServletContext();
		this.contextPath = sce.getServletContext().getContextPath();
		ctx.setAttribute(TemplateEngine.class.getName(), getTemplateEngine(ctx));
		ctx.setAttribute(IAccountService.class.getName(), getAccountService());
		ctx.setAttribute(IFilmService.class.getName(), getFilmService());
		ctx.setAttribute(ICategoryService.class.getName(), getCategoryService());
		ctx.setAttribute(ICountryService.class.getName(), getCountryService());
		ctx.setAttribute(IActorService.class.getName(), getActorService());
		ctx.setAttribute(FormObjectBinder.class.getName(), getFormObjectBinder());
		insertCategories();
		insertCountries();
		createIndexer();
	}

	private void createIndexer() {
		FullTextSession fullTextSession = Search.getFullTextSession(getSessionFactory().getCurrentSession());
		try {
			fullTextSession.createIndexer(
				Film.class, 
				Actor.class, 
				Director.class, 
				Country.class
			).startAndWait();
			fullTextSession.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			fullTextSession.close();
		}
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
		templateResolver.setCharacterEncoding("UTF-8");
		
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
	
	private IActorRepository getActorRepository() {
		if (actorRepository == null) {
			actorRepository = new ActorRepository(getSessionFactory());
		}
		return actorRepository;
	}
	
	private void insertCategories() {
		ICategoryRepository repo = getCategoryRepository();
		try {
			getTransactionTemplate().execute(() -> {
				repo.insert(new Category("comedy"));
				repo.insert(new Category("romantic"));
				repo.insert(new Category("action"));
				repo.insert(new Category("sic-fi"));
			});
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private void insertCountries() {
		ICountryRepository repo = getCountryRepository();
		try {
			getTransactionTemplate().execute(() -> {
				repo.insert(new Country("vi", "Viet Nam", 
						new ImageData(toStaticUrl("/images/flags/vietnam.png"))));
				repo.insert(new Country("usa", "United States of America",
						new ImageData(toStaticUrl("/images/flags/united-states-of-america.png"))));
				repo.insert(new Country("uk", "United Kingdom",
						new ImageData(toStaticUrl("/images/flags/united-kingdom.png"))));
				repo.insert(new Country("ca", "China",
						new ImageData(toStaticUrl("/images/flags/china.png"))));
				repo.insert(new Country("jap", "Japan",
						new ImageData(toStaticUrl("/images/flags/japan.png"))));
			});
		}catch (Exception e) {
			LOGGER.error("insert countries fail", e);
		}
	}
	
	private ICategoryRepository getCategoryRepository() {
		if (categoryRepository == null) {
			categoryRepository = new CategoryRepository(getSessionFactory());
		}
		return categoryRepository;
	}
	
	private ICountryRepository getCountryRepository() {
		if (countryRepository == null) {
			countryRepository = new CountryRepository(getSessionFactory());
		}
		return countryRepository;
	}
	
	private IAccountService getAccountService() {
		if (accountService == null) {
			accountService = new AccountService(getImageStorageService(), 
					getUserRepository(), getTransactionTemplate());
		}
		return accountService;
	}

	private IImageStorageService getImageStorageService() {
		if (imageStorageService == null) {
			try {
				imageStorageService = new ImageStorageService(
						getContextPath(), 
						FILE_DIRECTORY,
						FILE_RESOURCE_PREFIX,
						getImageDataRepository(), 
						getTransactionTemplate(),
						false
				);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return imageStorageService;
	}

	private IImageDataRepository getImageDataRepository() {
		if (imageDataRepository == null) {
			imageDataRepository = new ImageDataRepository(getSessionFactory());
		}
		return imageDataRepository;
	}
	
	private String getContextPath() {
		return contextPath;
	}

	public IFilmService getFilmService() {
		if (filmService == null) {
			filmService = new FilmService(
					getFilmRepository(), 
					getImageStorageService(), 
					getCountryRepository(), 
					getCategoryRepository(),
					getActorRepository(),
					getTransactionTemplate()
			);
		}
		return filmService;
	}

	public IFilmRepository getFilmRepository() {
		if (filmRepository == null) {
			filmRepository = new FilmRepository(getSessionFactory());
		}
		return filmRepository;
	}
	
	private ICategoryService getCategoryService() {
		if (categoryService == null) {
			categoryService = new CategoryService(getCategoryRepository(), getTransactionTemplate());
		}
		return categoryService;
	}
	
	
	private ICountryService getCountryService() {
		if (countryService == null) {
			countryService = new CountryService(getCountryRepository(), getTransactionTemplate());
		}
		return countryService;
	}
	
	private IActorService getActorService() {
		if (actorService == null) {
			actorService = new ActorService(getImageStorageService(), getActorRepository(), getCountryRepository(), getTransactionTemplate());
		}
		return actorService;
	}
	
	private ITransactionTemplate getTransactionTemplate() {
		if (transactionTemplate == null) {
			transactionTemplate = new HibernateTransactionTemplate(getSessionFactory());
		}
		return transactionTemplate;
	}
	
	private FormObjectBinder getFormObjectBinder() {
		if (formObjectBinder == null) {
			formObjectBinder = new FormObjectBinder();
			formObjectBinder
				.addConverter(String.class, new StringConverter())
				.addConverter(Integer.class, new IntegerConverter())
				.addConverter(Gender.class,	new GenderConverter())
				.addConverter(String[].class, new StringArrayConverter())
				.addConverter(Integer[].class, new IntegerArrayConverter());
		}
		return formObjectBinder;
	}
	
	private String toStaticUrl(String path) {
		return getContextPath() + STATIC_RESOURCE_PREFIX + path;
	}
	
	private String toFileUrl(String path) {
		return getContextPath() + FILE_RESOURCE_PREFIX + path;
	}
}
