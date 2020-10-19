package com.godofwibu.narga.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.exception.EmptyQueryException;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.godofwibu.narga.entities.Actor;
import com.godofwibu.narga.entities.Director;

public class ActorRepository implements IActorRepository {
	
	private SessionFactory sessionFactory; 

	public ActorRepository(SessionFactory sessionFactory) {
		super();
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<Actor> findAll() {
		List<Actor> actors = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			actors = session.createQuery("SELECT a FROM Actor as a", Actor.class)
					.getResultList();
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
		}
		return actors;
	}

	@Override
	public Actor findById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		Actor actor = null;
		try {
			transaction = session.beginTransaction();
			actor = session.get(Actor.class, id);
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public Integer insert(Actor entity) {
		Integer id = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			id = (Integer)session.save(entity);
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void update(Actor entity) {
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
	public void deleteById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("DELETE FROM Category AS c WHERE c.id=:category_id");
			query.setParameter("category_id", id);
			query.executeUpdate();
			transaction.commit();
		}catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Actor entity) {
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Actor> searchByName(String input, int maxResult) {
		FullTextSession fullTextSession = Search.getFullTextSession(sessionFactory.getCurrentSession());
		Transaction transaction = null;
		List<Actor> searhResult = null;
		try {
			transaction = fullTextSession.beginTransaction();
			QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
					.buildQueryBuilder()
					.forEntity(Director.class)
					.get();
			org.apache.lucene.search.Query luceneQuery = queryBuilder
					.keyword()
					.onFields("name", "country.name")
					.matching(input)
					.createQuery();
			javax.persistence.Query hibernateQuery = fullTextSession
					.createFullTextQuery(luceneQuery, Actor.class)
					.setMaxResults(maxResult);
			searhResult = hibernateQuery
					.getResultList();
			transaction.commit();
			fullTextSession.close();
			return searhResult;
		}catch (HibernateException e) {
			if (transaction != null && transaction.isActive())
				transaction.rollback();
			throw e;
		} catch (EmptyQueryException e) {
			return new ArrayList<Actor>();
		}finally {
			fullTextSession.close();
		}
	}

	@Override
	public List<Actor> findFirst(int maxResult) {
		List<Actor> actors = null;
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			actors = session.createQuery("FROM Actor", Actor.class)
					.setMaxResults(maxResult)
					.getResultList();
			tx.commit();
			return actors;
		} catch (Exception e) {
			if (tx != null && tx.isActive())
				tx.rollback();
			throw e;
		}
	}	
}
