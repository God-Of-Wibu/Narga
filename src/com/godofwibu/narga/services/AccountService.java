package com.godofwibu.narga.services;

import com.godofwibu.narga.entities.Gender;
import com.godofwibu.narga.entities.Profile;
import com.godofwibu.narga.entities.Role;
import com.godofwibu.narga.entities.User;
import com.godofwibu.narga.formdata.RegisterFormData;
import com.godofwibu.narga.repositories.IUserRepository;
import com.godofwibu.narga.services.exception.ServiceLayerException;
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
		return transactionTemplate.execute(() -> userRepository.findById(userId) );
	}

	@Override
	public User registerNewUser(RegisterFormData data) throws ServiceLayerException {
		return transactionTemplate.execute(() -> {
			User user = new User();
			user.setRole(Role.MEMBER);
			user.setId(data.getUserId());
			
			Profile profile = new Profile();
			profile.setGender(Gender.MALE);
			profile.setName(data.getName());
			
			user.setProfile(profile);
			
			if (!data.getPassword().equals(data.getRePassword()))
				throw new ServiceLayerException("password does not match with re-password");
			
			if (data.getPassword().length() < 4) {
				throw new ServiceLayerException("password must be at least 4 characters");
			}
			
			if (userRepository.hasUser(data.getUserId())) {
				throw new ServiceLayerException("user id "+ data.getUserId() + "are already used");
			}
			
			user.setPassword(data.getPassword());
			userRepository.save(user);
			
			return userRepository.findById(data.getUserId());
		});
	}


	

}
