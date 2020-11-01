package com.godofwibu.narga.services;

import com.godofwibu.narga.entities.Profile;
import com.godofwibu.narga.entities.Role;
import com.godofwibu.narga.entities.User;
import com.godofwibu.narga.repositories.IUserRepository;
import com.godofwibu.narga.utils.ITransactionTemplate;

public class AccountService implements IAccountService {

	private IUserRepository userRepository;
	private ITransactionTemplate transactionTemplate;

	public AccountService(IImageStorageService imageStorageService, IUserRepository userRepository,
			ITransactionTemplate executonWrapper) {
		super();
		this.userRepository = userRepository;
		this.transactionTemplate = executonWrapper;
	}

	@Override
	public User loadUserById(String userId) {
		return userRepository.findById(userId);
	}

	@Override
	public User registerNewUser(String userId, String password, String confirmPassword, Role role, String name,
			String personalId) throws UserCreationException {
		return transactionTemplate.execute(() -> {
			if (isUserAlreadyExist(userId))
				throw new UserCreationException("user already exist: " + userId, userId);
			if (!password.equals(confirmPassword))
				throw new UserCreationException("password does not match confirm password", userId);

			Profile profile = new Profile();
			profile.setPersonalId(personalId);
			profile.setName(name);

			User user = new User();
			user.setId(userId);
			user.setPassword(password);
			user.setRole(role);
			user.setProfile(profile);

			String createdUserId = userRepository.insert(user);
			return userRepository.findById(createdUserId);
		});
	}

	@Override
	public void changeUserPassword(String userId, String oldPassword, String newPassword, String confirmNewPassword)
			throws ServiceLayerException {

	}

	@Override
	public boolean checkPassword(String userId, String password) throws ServiceLayerException {
		return false;
	}

	@Override
	public boolean isUserAlreadyExist(String userId) throws ServiceLayerException {
		return userRepository.hasUser(userId);
	}

	@Override
	public boolean isStrongPassword(String password) throws ServiceLayerException {
		return false;
	}

	@Override
	public void updateUserProfile(String userId, Profile profile) throws ServiceLayerException {

	}

	@Override
	public Profile getProfileForUser(String userId) throws ServiceLayerException {
		return null;
	}

	@Override
	public boolean isPersonalIdInUsed(String personalId) throws ServiceLayerException {
		// TODO Auto-generated method stub
		return false;
	}

}
