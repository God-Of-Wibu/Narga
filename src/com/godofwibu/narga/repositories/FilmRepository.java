package com.godofwibu.narga.repositories;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.godofwibu.narga.entities.Film;

public class FilmRepository implements IFilmRepository {

	private SessionFactory sessionFactory;

	public FilmRepository(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Film> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Film findById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		Film film = null;
		try {
			transaction = session.beginTransaction();
			film = session.createQuery("SELECT * FORM film f WHERE f.id=:id", Film.class)
					.setParameter("id", id)
					.getSingleResult();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public Integer insert(Film entity) {
		Integer id = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			id = (Integer) session.save(entity);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void update(Film film) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.merge(film);
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Film entity) {
		// TODO Auto-generated method stub

	}

}
