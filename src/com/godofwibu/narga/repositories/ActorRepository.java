package com.godofwibu.narga.repositories;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.godofwibu.narga.entities.Actor;

public class ActorRepository implements IActorRepository {
	
	private SessionFactory sessionFactory; 

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
}
