package com.godofwibu.narga.services;

import com.godofwibu.narga.entities.Profile;
import com.godofwibu.narga.entities.Role;
import com.godofwibu.narga.entities.User;

public interface IAccountService {
	User loadUserById(String userId);

	User registerNewUser(String userId, String password, String confirmPassword, Role role, String name,
			String personalId) throws UserCreationException;

	void updateUserProfile(User user, Profile profile);

	void changeUserPassword(String userId, String oldPassword, String newPassword, String confirmNewPassword);

	boolean checkPassword(String userId, String password);
}
