package com.godofwibu.narga.services;

import com.godofwibu.narga.entities.Profile;
import com.godofwibu.narga.entities.Role;
import com.godofwibu.narga.entities.User;

public interface IAccountService {
	User getUser(String userId);

	User registerNewUser(String userId, String password, String confirmPassword, Role role, String name,
			String personalId) throws ServiceLayerException;

}
