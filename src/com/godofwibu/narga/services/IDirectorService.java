package com.godofwibu.narga.services;

import com.godofwibu.narga.formdata.AddDirectorFormData;
import com.godofwibu.narga.services.exception.ServiceLayerException;

public interface IDirectorService {

	void addNewDirector(AddDirectorFormData formData) throws ServiceLayerException;

	String search(String parameter) throws ServiceLayerException;

}
