package com.godofwibu.narga.repositories;

import org.hibernate.SessionFactory;

import com.godofwibu.narga.entities.Category;

public class CategoryRepository extends CurdRepository<Category, String> implements ICategoryRepository {
	public CategoryRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Category.class);
	}
}
