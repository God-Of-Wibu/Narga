package com.godofwibu.narga.repositories;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.godofwibu.narga.entities.User;

public class UserRepository implements IUserRepository {

	private SessionFactory sessionFactory;
	
	

	public UserRepository(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<User> getAll() {

		List<User> users = new ArrayList<User>();

		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		users.addAll(session.createQuery("form users", User.class).getResultList());
		transaction.commit();

		return users;
	}

	@Override
	public User get(String id) {
		Session session = null;
		Transaction transaction = null;
		User user = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			user = session.get(User.class, id);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
		return user;
	}

	@Override
	public void update(User entity) {

	}

	@Override
	public void delete(String id) {

	}

}
