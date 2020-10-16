package com.godofwibu.narga.repositories;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.godofwibu.narga.entities.ImageData;

public class ImageDataRepository implements IImageDataRepository {
	private SessionFactory sessionFactory;

	public ImageDataRepository(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<ImageData> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageData findById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		ImageData imageData = null;
		try {
			transaction = session.beginTransaction();
			imageData = session.createQuery("FROM ImageData img WHERE img.id=:id", ImageData.class)
					.setParameter("id", id)
					.getSingleResult();
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
		}
		return imageData;

	}

	@Override
	public Integer insert(ImageData entity) {
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
	public void update(ImageData entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(ImageData entity) {
		// TODO Auto-generated method stub

	}

}
