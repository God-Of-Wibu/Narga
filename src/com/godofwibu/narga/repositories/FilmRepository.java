package com.godofwibu.narga.repositories;

import java.util.List;

import org.apache.lucene.search.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.godofwibu.narga.entities.Film;

public class FilmRepository implements IFilmRepository {

	private SessionFactory sessionFactory;

	public FilmRepository(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Film> findAll() {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		List<Film> films = null;
		try {
			transaction = session.beginTransaction();
			films = session.createQuery("FROM Film", Film.class)
					.getResultList();
			transaction.commit();
			return films;
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			throw e;
		}
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
			return film;
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			throw e;
		}
		
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
			throw e;
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
			throw e;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Film> search(String input) {
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
		Transaction transaction = null;
		List<Film> searchResult = null;
		try {
		transaction = fullTextSession.beginTransaction();
		QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
				.buildQueryBuilder()
				.forEntity(Film.class)
				.get();
		Query luceneQuery = queryBuilder
				.keyword()
				.onFields("title", "casting.name", "director.name", "country.name")
				.matching(input)
				.createQuery();
		javax.persistence.Query jpaQuery = fullTextSession.createFullTextQuery(luceneQuery, Film.class);
		searchResult = jpaQuery
				.setMaxResults(5)
				.getResultList();
		transaction.commit();
		fullTextSession.close();
		return searchResult;
		} catch (HibernateException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			fullTextSession.close();
		}
	}

}
