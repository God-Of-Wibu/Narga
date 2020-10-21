package com.godofwibu.narga.services;

import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;

import com.godofwibu.narga.entities.Actor;
import com.godofwibu.narga.entities.Country;
import com.godofwibu.narga.entities.Gender;
import com.godofwibu.narga.entities.ImageData;
import com.godofwibu.narga.repositories.IActorRepository;
import com.godofwibu.narga.repositories.ICountryRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ActorService implements IActorService {

	private IImageStorageService imageStorageService;
	private IActorRepository actorRepository;
	private ICountryRepository countryRepository;
	private Gson gson;
	private TransactionalOperationExecutor operationExecutor;

	public ActorService(IImageStorageService imageStorageService, IActorRepository actorRepository,
			ICountryRepository countryRepository, TransactionalOperationExecutor operationExecutor) {
		super();
		this.imageStorageService = imageStorageService;
		this.actorRepository = actorRepository;
		this.countryRepository = countryRepository;
		this.operationExecutor = operationExecutor;
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	}

	@Override
	public void addNewActor(String name, int age, Gender gender, String countryName, Part avatarPart)
			throws ServiceLayerException {
		operationExecutor.execute(() -> {
			Country country = countryRepository.findById(countryName);
			Actor actor = new Actor();
			actor.setName(name);
			actor.setAge(age);
			actor.setCountry(country);

			Integer actorId = actorRepository.insert(actor);

			String avatarExtension = extractFileExtensionFromPart(avatarPart);
			String fileName = "actor_avatar_" + actorId + "." + avatarExtension;

			ImageData imageData = imageStorageService.saveImage(avatarPart.getInputStream(), fileName);

			actor.setAvatar(imageData);
			actor.setId(actorId);
			actorRepository.update(actor);
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
		return operationExecutor.execute(() -> gson.toJson(actorRepository.findAll()));
	}

	@Override
	public String searchActorAsJson(String input, int maxResult) throws ServiceLayerException {
		return operationExecutor.execute(() -> gson.toJson(actorRepository.searchByName(input, maxResult)));
	}

	@Override
	public String getFirstActorsAsJson(int maxResult) throws ServiceLayerException {
		return operationExecutor.execute(() -> gson.toJson(actorRepository.findFirst(maxResult)));
	}

}
