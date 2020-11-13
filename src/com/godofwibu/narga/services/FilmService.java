package com.godofwibu.narga.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.godofwibu.narga.entities.Category;
import com.godofwibu.narga.entities.Country;
import com.godofwibu.narga.entities.Film;
import com.godofwibu.narga.entities.ImageData;
import com.godofwibu.narga.entities.Issue;
import com.godofwibu.narga.dto.AddFilmFormData;
import com.godofwibu.narga.dto.FilmDTO;
import com.godofwibu.narga.entities.Actor;
import com.godofwibu.narga.repositories.IActorRepository;
import com.godofwibu.narga.repositories.ICategoryRepository;
import com.godofwibu.narga.repositories.ICountryRepository;
import com.godofwibu.narga.repositories.IFilmRepository;
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
	private IIssueService issueService;
	private Gson gson;
	private ITransactionTemplate transactionTemplate;

	public FilmService(IFilmRepository filmRepository, IImageStorageService imageStorageService,
			ICountryRepository countryRepository, ICategoryRepository categoryRepository, IActorRepository actorRepository, ITransactionTemplate transactionTemplate) {
		super();
		this.filmRepository = filmRepository;
		this.imageStorageService = imageStorageService;
		this.countryRepository = countryRepository;
		this.categoryRepository = categoryRepository;
		this.transactionTemplate = transactionTemplate;
		this.actorRepository = actorRepository;
		
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
				film.setDirector(null);
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
				
				Integer filmId = filmRepository.insert(film);
	
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
		return transactionTemplate.execute(() -> filmRepository.findById(filmId));
	}

	@Override
	public List<FilmDTO> getFilmInWeek() throws ServiceLayerException {
		return transactionTemplate.execute(() -> {
			List<FilmDTO> films = new ArrayList<FilmDTO>();
			List<Issue> issues = issueService.getIssuesInThisWeek();
			for (Issue issue : issues) {
				Film film = issue.getFilm();
				films.add(new FilmDTO(film.getId(),film.getPoster().getUrl()));
			}
			return films;
		}) ;
	}

	@Override
	public List<FilmDTO> getHotFilms() throws ServiceLayerException {
		// TODO Auto-generated method stub
		return null;
	}

}
