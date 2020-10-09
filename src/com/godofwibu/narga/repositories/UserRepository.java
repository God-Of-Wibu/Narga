package com.godofwibu.narga.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
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
	public List<User> findAll() {
		List<User> users = new ArrayList<User>();
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			users.addAll(session.createQuery("form users", User.class).getResultList());
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
		}

		return users;
	}

	@Override
	public User findById(String id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		User user = null;
		try {

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
	public void update(User user) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			
			transaction = session.beginTransaction();
			session.update(user);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	@Override
	public void deleteById(String id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {		
			transaction = session.beginTransaction();
			Query query = session.createQuery("DELETE FROM User AS u WHERE u.id=:user_id");
			query.setParameter("user_id", id);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

	@Override
	public String insert(User user) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		String userId = null;
		try {
			transaction = session.beginTransaction();
			userId = (String) session.save(user);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
		return userId;
	}

	@Override
	public void delete(User entity) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(entity);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}
	}

}
