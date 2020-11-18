package com.godofwibu.narga.services;

import com.godofwibu.narga.entities.User;
import com.godofwibu.narga.formdata.RegisterFormData;
import com.godofwibu.narga.services.exception.ServiceLayerException;

public interface IAccountService {
	User getUser(String userId);

	User registerNewUser(RegisterFormData data) throws ServiceLayerException;

}
