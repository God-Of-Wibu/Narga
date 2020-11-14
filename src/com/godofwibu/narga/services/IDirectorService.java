package com.godofwibu.narga.services;


import com.godofwibu.narga.dto.AddDirectorFormData;
import com.godofwibu.narga.dto.DirectorDetail;

public interface IDirectorService {

	void addNewDirector(AddDirectorFormData formData) throws ServiceLayerException;
	DirectorDetail getDirectorDetail (Integer Id) throws ServiceLayerException;

}
