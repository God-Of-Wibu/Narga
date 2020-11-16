package com.godofwibu.narga.services;

import java.io.IOException;

import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;

import com.godofwibu.narga.dto.Actor_DTO_ActorDetail;
import com.godofwibu.narga.entities.Actor;
import com.godofwibu.narga.entities.Country;
import com.godofwibu.narga.entities.ImageData;
import com.godofwibu.narga.formdata.AddActorFormData;
import com.godofwibu.narga.repositories.IActorRepository;
import com.godofwibu.narga.repositories.ICountryRepository;
import com.godofwibu.narga.utils.ITransactionTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ActorService implements IActorService {

	private IImageStorageService imageStorageService;
	private IActorRepository actorRepository;
	private ICountryRepository countryRepository;
	private Gson gson;
	private ITransactionTemplate transactionTemplate;

	public ActorService(IImageStorageService imageStorageService, IActorRepository actorRepository,
			ICountryRepository countryRepository, ITransactionTemplate transactionTemplate) {
		super();
		this.imageStorageService = imageStorageService;
		this.actorRepository = actorRepository;
		this.countryRepository = countryRepository;
		this.transactionTemplate = transactionTemplate;
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	}

	@Override
	public void addNewActor(AddActorFormData formData) throws ServiceLayerException {
		transactionTemplate.execute(() -> {
			try {
				Country country = countryRepository.findById(formData.getCountryId());
				Actor actor = new Actor();
				actor.setName(formData.getName());
				actor.setAge(formData.getAge());
				actor.setCountry(country);

				Integer actorId = actorRepository.insert(actor);

				if (formData.getAvatarPart() != null) {
					String avatarExtension = extractFileExtensionFromPart(formData.getAvatarPart());
					String fileName = "actor_avatar_" + actorId + "." + avatarExtension;

					ImageData imageData = imageStorageService.saveImage(formData.getAvatarPart(), fileName);

					actor.setAvatar(imageData);
					actor.setId(actorId);
					actorRepository.update(actor);
				}
			} catch (IOException e) {
				throw new ServiceLayerException("Failed to save film data" ,e);
			}
		});
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
	public String getAllActorsAsJson() throws ServiceLayerException {
		return transactionTemplate.execute(() -> gson.toJson(actorRepository.findAll()));
	}

	@Override
	public String searchActorAsJson(String input, int maxResult) throws ServiceLayerException {
		transactionTemplate.execute(() -> System.out.println(gson.toJson(actorRepository.searchByName(input, maxResult))));
		return transactionTemplate.execute(() -> gson.toJson(actorRepository.searchByName(input, maxResult)));
	}

	@Override
	public Actor_DTO_ActorDetail getActorDetail(Integer id) throws ServiceLayerException {
		return transactionTemplate.execute(()->{
			return new Actor_DTO_ActorDetail(actorRepository.findById(id));
	});
	}
}
