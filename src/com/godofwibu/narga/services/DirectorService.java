package com.godofwibu.narga.services;

import java.io.IOException;

import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;

import com.godofwibu.narga.dto.DirectorDetail;
import com.godofwibu.narga.entities.Actor;
import com.godofwibu.narga.entities.Country;
import com.godofwibu.narga.entities.Director;
import com.godofwibu.narga.entities.ImageData;
import com.godofwibu.narga.formdata.AddActorFormData;
import com.godofwibu.narga.formdata.AddDirectorFormData;
import com.godofwibu.narga.repositories.DataAccessLayerException;
import com.godofwibu.narga.repositories.ICountryRepository;
import com.godofwibu.narga.repositories.IDirectorRepository;
import com.godofwibu.narga.services.exception.ServiceLayerException;
import com.godofwibu.narga.utils.ITransactionTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DirectorService implements IDirectorService {
	private IImageStorageService imageStorageService;
	private IDirectorRepository directorRepository;
	private ICountryRepository countryRepository;
	private Gson gson;
	private ITransactionTemplate transactionTemplate;

	public DirectorService(IImageStorageService imageStorageService, IDirectorRepository directorRepository,
			ICountryRepository countryRepository, ITransactionTemplate transactionTemplate) {
		super();
		this.imageStorageService = imageStorageService;
		this.directorRepository = directorRepository;
		this.countryRepository = countryRepository;
		this.transactionTemplate = transactionTemplate;
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
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
	public void addNewDirector(AddDirectorFormData formData) throws ServiceLayerException {
		try {
			transactionTemplate.execute(() -> {
				Country country = countryRepository.findById(formData.getCountryId());
				Director director = new Director();
				director.setAge(formData.getAge());
				director.setCountry(country);
				director.setGender(formData.getGender());
				director.setName(formData.getName());

				Integer directorId = directorRepository.save(director);

				if (formData.getAvatarPart() != null) {
					String avatarExtension = extractFileExtensionFromPart(formData.getAvatarPart());
					String nameAva = "_director_avatar_" + directorId + "." + avatarExtension;

					ImageData imageData = imageStorageService.saveImage(formData.getAvatarPart(), nameAva);
					
					director.setAvatar(imageData);
					director.setId(directorId);
					directorRepository.update(director);
				}
			});
		} catch (DataAccessLayerException e) {
			throw new ServiceLayerException("save director data to database fail", e);
		}

	}

	@Override
	public String search(String input) throws ServiceLayerException {
		return transactionTemplate.execute(() -> gson.toJson(directorRepository.search(input)));
	}

	@Override
	public DirectorDetail getDirectorDetail(Integer Id) throws ServiceLayerException {
		return transactionTemplate.execute(()->{
			return new DirectorDetail(directorRepository.findById(Id));
		});
	}
}

