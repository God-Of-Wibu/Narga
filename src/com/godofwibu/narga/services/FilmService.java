package com.godofwibu.narga.services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.godofwibu.narga.entities.Category;
import com.godofwibu.narga.entities.Country;
import com.godofwibu.narga.entities.Director;
import com.godofwibu.narga.entities.Film;
import com.godofwibu.narga.entities.ImageData;
import com.godofwibu.narga.entities.Issue;
import com.godofwibu.narga.formdata.AddFilmFormData;
import com.godofwibu.narga.dto.Film_DTO_Home;
import com.godofwibu.narga.entities.Actor;
import com.godofwibu.narga.repositories.DataAccessLayerException;
import com.godofwibu.narga.repositories.IActorRepository;
import com.godofwibu.narga.repositories.ICategoryRepository;
import com.godofwibu.narga.repositories.ICountryRepository;
import com.godofwibu.narga.repositories.IDirectorRepository;
import com.godofwibu.narga.repositories.IFilmRepository;
import com.godofwibu.narga.services.exception.ServiceLayerException;
import com.godofwibu.narga.utils.ITransactionTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FilmService implements IFilmService {
	private final static Logger LOGGER = LoggerFactory.getLogger(FilmService.class);

	private IFilmRepository filmRepository;
	private IImageStorageService imageStorageService;
	private ICountryRepository countryRepository;
	private ICategoryRepository categoryRepository;
	private IActorRepository actorRepository;
	private Gson gson;
	private ITransactionTemplate transactionTemplate;
	private IDirectorRepository directorRepository;

	public FilmService(IFilmRepository filmRepository, IImageStorageService imageStorageService,
			ICountryRepository countryRepository, ICategoryRepository categoryRepository, IActorRepository actorRepository, ITransactionTemplate transactionTemplate, IDirectorRepository directorRepository) {
		super();
		this.filmRepository = filmRepository;
		this.imageStorageService = imageStorageService;
		this.countryRepository = countryRepository;
		this.categoryRepository = categoryRepository;
		this.transactionTemplate = transactionTemplate;
		this.actorRepository = actorRepository;
		this.directorRepository = directorRepository;
		
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	}

	@Override
	public void addNewFilm(AddFilmFormData formData) throws ServiceLayerException {

		transactionTemplate.execute(() -> {
			try {
				String extension = extractFileExtensionFromPart(formData.getPosterPart());
				
				Country country = countryRepository.findByName(formData.getCountryName());
				
				Film film = new Film();
				film.setTitle(formData.getTitle());
				film.setCountry(country);
				film.setRunningTime(formData.getRunningTime());
				film.setDirector(formData.getDirector() != null ? directorRepository.findById(formData.getDirector()) : null);
				film.setDescription(formData.getDescription());
	
				Set<Category> categories = new HashSet<Category>();
				Category category = null;
				for (Integer categoryId : formData.getCategories()) {
					if ((category = categoryRepository.findById(categoryId)) != null) {
						categories.add(category);
					}
				}
				film.setCategories(categories);
				
				Set<Actor> casting = new HashSet<>();
				Actor actor = null;
				for (Integer actorId : formData.getCasting()) {
					if ((actor = actorRepository.findById(actorId)) != null) {
						casting.add(actor);
					}
				}
				film.setCasting(casting);
				
				Integer filmId = filmRepository.save(film);
	
				String fileName = "film_poster_" + filmId + "." + extension;
				ImageData posterImageData = imageStorageService.saveImage(formData.getPosterPart().getInputStream(), fileName);
	
				film.setId(filmId);
				film.setPoster(posterImageData);
	
				filmRepository.update(film);
			} catch (IOException e) {
				throw new ServiceLayerException("Unable to get input stream form part", e);
			}
		});
	}

	@Override
	public String getAllFilmAsJson() throws ServiceLayerException {
		return transactionTemplate.execute(() -> gson.toJson(filmRepository.findAll()));
	}

	@Override
	public String searchFilmAsJson(String input) throws ServiceLayerException {
		return transactionTemplate.execute(() -> gson.toJson(filmRepository.search(input, 15)));
	}

	private String extractFileExtensionFromPart(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return FilenameUtils.getExtension(content.substring(content.indexOf('=') + 1).trim().replace("\"", ""));
			}
		}
		return null;
	}

	@Override
	public Film getFilmDetail(int filmId) throws ServiceLayerException {
		transactionTemplate.execute(() -> {
			Film f = filmRepository.findById(filmId);
			
			Director dir =f.getDirector();
		});
		return transactionTemplate.execute(() -> filmRepository.findById(filmId));
	}

	@Override
	public List<Film_DTO_Home> getFilmInThisWeek() throws ServiceLayerException {
		return transactionTemplate.execute(() -> {
			List<Film_DTO_Home> film_DTO_Homes = new ArrayList<>();
			
			SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
			String d1 = format.format(getFirstDaysOfCurrentWeek());
			String d2 = format.format(getLastDayOfThisWeek());
			
			List<Film> films = filmRepository.findHasIssueBetween(getFirstDaysOfCurrentWeek(), getLastDayOfThisWeek());
			
			filmRepository.findHasIssueBetween(getFirstDaysOfCurrentWeek(), getLastDayOfThisWeek())
				.forEach(film -> film_DTO_Homes.add(new Film_DTO_Home(film.getTitle(), film.getId(), film.getPoster().getUrl())));
			return film_DTO_Homes;
		}) ;
	}

	@Override
	public List<Film_DTO_Home> getHotFilms() throws ServiceLayerException {
		return null;
	}

	@Override
	public Film getFilm(int filmId) throws ServiceLayerException {
		try {
			return transactionTemplate.execute(() -> filmRepository.findById(filmId));
		} catch (DataAccessLayerException e) {
			throw new ServiceLayerException("execute operation failed", e);
		}
	}
	
	private java.sql.Date getFirstDaysOfCurrentWeek() {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return utilDateToSqlDate(cal.getTime());
	}
	
	private java.sql.Date getLastDayOfThisWeek() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		
		
		
		return utilDateToSqlDate(cal.getTime());
	}
	
	private java.sql.Date utilDateToSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
	
	
	
	
	
	

}
