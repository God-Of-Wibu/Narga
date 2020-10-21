package com.godofwibu.narga.repositories;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class CurdRepository<E, I extends Serializable> implements ICurdRepository<E, I> {
	
	private SessionFactory sessionFactory;
	private Class<E> entityType;

	public CurdRepository(SessionFactory sessionFactory, Class<E> entityType) {
		super();
		this.sessionFactory = sessionFactory;
		this.entityType = entityType;
	}

	@Override
	public List<E> findAll() {
		return (List<E>) getSession()
				.createQuery("FROM " + entityType.getName(), entityType)
				.getResultList();
	}

	@Override
	public E findById(I id) {
		return getSession()
				.get(entityType, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public I insert(E entity) {
		return (I) getSession()
				.save(entity);
	}

	@Override
	public void update(E entity) {
		getSession().update(entity);
	}

	@Override
	public void deleteById(I id) {
		E entity = findById(id);
		delete(entity);
	}

	@Override
	public void delete(E entity) {
		getSession().delete(entity);
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
