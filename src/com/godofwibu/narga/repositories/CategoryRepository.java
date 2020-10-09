package com.godofwibu.narga.repositories;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.godofwibu.narga.entities.Category;

public class CategoryRepository implements ICategoryRepository {
	
	private SessionFactory sessionFactory;
	
	public CategoryRepository(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Category> getAll() {
		return sessionFactory.getCurrentSession().createQuery("SELECT c FROM Category AS c").getResultList();
	}

	@Override
	public Category findById(String id) {
		return sessionFactory.getCurrentSession().get(Category.class, id);
	}

	@Override
	public String insert(Category entity) {
		String id = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(entity);
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void update(Category entity) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(entity);
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(String id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("DELETE FROM Category AS c WHERE c.id=:category_id");
			query.setParameter("category_id", id);
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Category entity) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(entity);
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

}
