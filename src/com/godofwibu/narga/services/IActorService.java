package com.godofwibu.narga.services;

import com.godofwibu.narga.dto.AddActorFormData;

public interface IActorService {
	void addNewActor(AddActorFormData formData) throws ServiceLayerException;
	String getAllActorsAsJson() throws ServiceLayerException;
	String searchActorAsJson(String input, int maxResult) throws ServiceLayerException;
}
