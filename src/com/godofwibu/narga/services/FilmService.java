package com.godofwibu.narga.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.godofwibu.narga.entities.Category;
import com.godofwibu.narga.entities.Country;
import com.godofwibu.narga.entities.Film;
import com.godofwibu.narga.entities.ImageData;
import com.godofwibu.narga.repositories.ICategoryRepository;
import com.godofwibu.narga.repositories.ICountryRepository;
import com.godofwibu.narga.repositories.IFilmRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FilmService implements IFilmService {
	private final static Logger LOGGER = LoggerFactory.getLogger(FilmService.class);

	private IFilmRepository filmRepository;
	private IImageStorageService imageStorageService;
	private ICountryRepository countryRepository;
	private ICategoryRepository categoryRepository;
	private Gson gson;
	private TransactionalOperationExecutor operationExecutor;

	private static final String IMAGE_FILE_EXTENSIONS[] = { "png", "jpg", "jpeg" };

	public FilmService(IFilmRepository filmRepository, IImageStorageService imageStorageService,
			ICountryRepository countryRepository, ICategoryRepository categoryRepository, TransactionalOperationExecutor operationExecutor) {
		super();
		this.filmRepository = filmRepository;
		this.imageStorageService = imageStorageService;
		this.countryRepository = countryRepository;
		this.categoryRepository = categoryRepository;
		this.operationExecutor = operationExecutor;
		
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	}

	@Override
	public void addNewFilm(String title, Part poster, String countryName, String director, int runningTime,
			String[] categories, String[] casting) throws ServiceLayerException {

		operationExecutor.execute(() -> {
			String extension = extractFileExtensionFromPart(poster);
			if (!isIsValidImageFileExtension(extension))
				throw new ServiceLayerException("poster must be image file: " + Arrays.toString(IMAGE_FILE_EXTENSIONS));

			Country country = countryRepository.findByName(countryName);
			if (country == null)
				throw new ServiceLayerException("country name is incorect:" + countryName);
			Film film = new Film();
			film.setTitle(title);
			film.setCountry(country);
			film.setRunningTime(runningTime);
			film.setDirector(null);

			Set<Category> cats = new HashSet<Category>();
			Category cat = null;
			for (String category : categories) {
				if ((cat = categoryRepository.findById(category)) != null) {
					cats.add(cat);
				}
			}
			film.setCategories(cats);

			Integer filmId = filmRepository.insert(film);

			String fileName = "film_poster_" + filmId + "." + extension;
			ImageData posterImageData = imageStorageService.saveImage(poster.getInputStream(), fileName);

			film.setId(filmId);
			film.setPoster(posterImageData);

			filmRepository.update(film);
		});

	}

	@Override
	public String getAllFilmAsJson() throws ServiceLayerException {
		return operationExecutor.execute(() -> gson.toJson(filmRepository.findAll()));
	}

	@Override
	public String searchFilmAsJson(String input) throws ServiceLayerException {
		return operationExecutor.execute(() -> gson.toJson(filmRepository.search(input, 15)));
	}

	private boolean isIsValidImageFileExtension(String extension) {
		for (String ext : IMAGE_FILE_EXTENSIONS) {
			if (extension.equals(ext))
				return true;
		}
		return false;
	}

	private String extractFileExtensionFromPart(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return FilenameUtils.getExtension(content.substring(content.indexOf('=') + 1).trim().replace("\"", ""));
			}
		}
		return null;
	}

}
