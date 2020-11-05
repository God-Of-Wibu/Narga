package com.godofwibu.narga.services;

import com.godofwibu.narga.dto.AddDirectorFormData;

public interface IDirectorService {

	void addNewDirector(AddDirectorFormData formData) throws ServiceLayerException;

}
