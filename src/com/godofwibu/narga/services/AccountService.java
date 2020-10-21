package com.godofwibu.narga.services;

import com.godofwibu.narga.entities.Profile;
import com.godofwibu.narga.entities.Role;
import com.godofwibu.narga.entities.User;
import com.godofwibu.narga.repositories.IUserRepository;

public class AccountService implements IAccountService {

	private IImageStorageService imageStorageService;
	private IUserRepository userRepository;
	private TransactionalOperationExecutor operationExecutor;

	

	public AccountService(IImageStorageService imageStorageService, IUserRepository userRepository,
			TransactionalOperationExecutor operationExecutor) {
		super();
		this.imageStorageService = imageStorageService;
		this.userRepository = userRepository;
		this.operationExecutor = operationExecutor;
	}

	@Override
	public User loadUserById(String userId) {
		return userRepository.findById(userId);
	}

	@Override
	public User registerNewUser(String userId, String password, String confirmPassword, Role role, String name,
			String personalId) throws UserCreationException {

		if (userId == null || password == null || confirmPassword == null || role == null || name == null
				|| personalId == null)
			throw new UserCreationException("you must fill all required fileds", null);
		if (userRepository.hasUser(userId))
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

		
		userRepository.insert(user);

		return null;
	}

	@Override
	public void updateUserProfile(User user, Profile profile) {
	}

	@Override
	public void changeUserPassword(String userId, String oldPassword, String newPassword, String confirmNewPassword) {

	}

	@Override
	public boolean checkPassword(String userId, String password) {
		User user = userRepository.findById(userId);
		return user != null && user.getPassword().equals(password);
	}

}
