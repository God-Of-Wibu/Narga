package com.godofwibu.narga.repositories;

import org.hibernate.SessionFactory;

import com.godofwibu.narga.entities.User;

public class UserRepository extends CrudRepository<User, String> implements IUserRepository {

	public UserRepository(SessionFactory sessionFactory) {
		super(sessionFactory, User.class);
	}

	@Override
	public boolean hasUser(String userId) throws DataAccessLayerException {
		return getSession().createQuery("FROM User AS user_ WHERE user_.id=:userId", User.class)
				.setParameter("userId", userId)
				.uniqueResult() != null;
	}
}
