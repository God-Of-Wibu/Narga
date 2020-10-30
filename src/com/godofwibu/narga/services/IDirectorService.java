package com.godofwibu.narga.services;

import com.godofwibu.narga.dto.AddActorFormData;

public interface IDirectorService {

	void addNewDirector(AddActorFormData formData) throws ServiceLayerException;

}
