package com.godofwibu.narga.repositories;

import javax.persistence.PersistenceException;

import org.hibernate.SessionFactory;

import com.godofwibu.narga.entities.Category;

public class CategoryRepository extends CrudRepository<Category, Integer> implements ICategoryRepository {
	public CategoryRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Category.class);
	}

	@Override
	public Category findByName(String name) throws DataAccessLayerException {
		return getSession()
				.createQuery("SELECT * FORM Category c WHERE c.name=:category_name", Category.class)
				.setParameter("category_name", name)
				.uniqueResult();
	}
}
