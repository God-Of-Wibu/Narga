package com.godofwibu.narga.repositories;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.godofwibu.narga.entities.Director;

public class DirectorRepository implements IDirectorRepository {
	private SessionFactory sessionFactory;
	
	public DirectorRepository(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Director> findAll() {
		List<Director> actors = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			actors = session.createQuery("FROM Director", Director.class)
					.getResultList();
			tx.commit();
			return actors;
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			throw e;
		}
		
	}

	@Override
	public Director findById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		Director director = null;
		try {
			transaction = session.beginTransaction();
			director = session.get(Director.class, id);
			transaction.commit();
			return director;
		}catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			throw e;
		}
	}

	@Override
	public Integer insert(Director director) {
		Integer id = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			id = (Integer)session.save(director);
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void update(Director director) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(director);
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void deleteById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("DELETE FROM Director AS this_ WHERE this_.id=:director_id");
			query.setParameter("director_id", id);
			query.executeUpdate();
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		}
	}

	@Override
	public void delete(Director director) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(director);
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Director> search(String input) {
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
		Transaction transaction = null;
		List<Director> searhResult = null;
		try {
			transaction = fullTextSession.beginTransaction();
			QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
					.buildQueryBuilder()
					.forEntity(Director.class)
					.get();
			org.apache.lucene.search.Query luceneQuery = queryBuilder
					.keyword()
					.onField("name")
					.matching(input)
					.createQuery();
			javax.persistence.Query hibernateQuery = fullTextSession.createFullTextQuery(luceneQuery, Director.class);
			
			searhResult = hibernateQuery
					.setMaxResults(10)
					.getResultList();
			transaction.commit();
			fullTextSession.close();
			return searhResult;
		}catch (HibernateException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} finally {
			fullTextSession.close();
		}
	}	
}
