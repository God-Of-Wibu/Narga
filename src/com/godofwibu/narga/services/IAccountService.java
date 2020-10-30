package com.godofwibu.narga.services;

import com.godofwibu.narga.entities.Profile;
import com.godofwibu.narga.entities.Role;
import com.godofwibu.narga.entities.User;

public interface IAccountService {
	User loadUserById(String userId);

	User registerNewUser(String userId, String password, String confirmPassword, Role role, String name,
			String personalId) throws ServiceLayerException;

	void updateUserProfile(String userId, Profile profile) throws ServiceLayerException;
	
	Profile getProfileForUser(String userId) throws ServiceLayerException;

	void changeUserPassword(String userId, String oldPassword, String newPassword, String confirmNewPassword);

	boolean checkPassword(String userId, String password) throws ServiceLayerException;
	
	boolean isUserAlreadyExist(String userId) throws ServiceLayerException;
	
	boolean isStrongPassword(String password) throws ServiceLayerException;
	
	boolean isPersonalIdInUsed(String personalId) throws ServiceLayerException;
}
