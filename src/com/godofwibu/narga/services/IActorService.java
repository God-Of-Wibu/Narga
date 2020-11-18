package com.godofwibu.narga.services;

import com.godofwibu.narga.dto.Actor_DTO_ActorDetail;
import com.godofwibu.narga.formdata.AddActorFormData;
import com.godofwibu.narga.services.exception.ServiceLayerException;

public interface IActorService {
	void addNewActor(AddActorFormData formData) throws ServiceLayerException;
	String getAllActorsAsJson() throws ServiceLayerException;
	String searchActorAsJson(String input, int maxResult) throws ServiceLayerException;
	Actor_DTO_ActorDetail getActorDetail (Integer Id) throws ServiceLayerException;
}
