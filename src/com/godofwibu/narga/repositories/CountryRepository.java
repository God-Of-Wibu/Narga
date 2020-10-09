package com.godofwibu.narga.repositories;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.godofwibu.narga.entities.Country;

public class CountryRepository implements ICountryRepository {

	private SessionFactory sessionFactory;
	
	
	public CountryRepository(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Country> findAll() {
		List<Country> countries = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			countries = session.createQuery("SELECT c FROM Country as c", Country.class)
					.getResultList();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
		}
		return countries;
	}

	@Override
	public Country findById(String id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		Country country = null;
		try {
			transaction = session.beginTransaction();
			country = session.get(Country.class, id);
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return country;
	}

	@Override
	public String insert(Country entity) {
		String id = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			id = (String)session.save(entity);
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void update(Country entity) {
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
			Query query = session.createQuery("DELETE FROM Country AS c WHERE c.id=:country_id");
			query.setParameter("country_id", id);
			query.executeUpdate();
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Country entity) {
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
