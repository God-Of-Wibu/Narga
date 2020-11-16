package com.godofwibu.narga.services;

import com.godofwibu.narga.entities.Role;
import com.godofwibu.narga.entities.User;
import com.godofwibu.narga.repositories.IUserRepository;
import com.godofwibu.narga.utils.ITransactionTemplate;

public class AccountService implements IAccountService {

	private IUserRepository userRepository;
	private ITransactionTemplate transactionTemplate;

	public AccountService(IImageStorageService imageStorageService, IUserRepository userRepository,
			ITransactionTemplate transactionTemplate) {
		super();
		this.userRepository = userRepository;
		this.transactionTemplate = transactionTemplate;
	}

	@Override
	public User getUser(String userId) {
		return userRepository.findById(userId);
	}

	@Override
	public User registerNewUser(String userId, String password, String confirmPassword, Role role, String name,
			String personalId) throws UserCreationException {
		return transactionTemplate.execute(() -> {
			return null;
		});
	}


	

}
