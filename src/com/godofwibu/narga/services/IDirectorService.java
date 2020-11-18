package com.godofwibu.narga.services;


import com.godofwibu.narga.formdata.AddDirectorFormData;
import com.godofwibu.narga.services.exception.ServiceLayerException;
import com.godofwibu.narga.dto.DirectorDetail;


public interface IDirectorService {

	void addNewDirector(AddDirectorFormData formData) throws ServiceLayerException;
	DirectorDetail getDirectorDetail (Integer Id) throws ServiceLayerException;

	String search(String parameter) throws ServiceLayerException;

}
