package com.godofwibu.narga.repositories;

import com.godofwibu.narga.entities.User;

public interface IUserRepository extends ICurdRepository<User, String>{
	boolean hasUser(String userId);
}
