package com.godofwibu.narga.repositories;

import com.godofwibu.narga.entities.Category;

public interface ICategoryRepository extends ICurdRepository<Category, Integer> {
	Category findByName(String name);
}
