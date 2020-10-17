package com.godofwibu.narga.services;

import java.util.List;

import com.godofwibu.narga.entities.Category;
import com.godofwibu.narga.repositories.ICategoryRepository;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CategoryService implements ICategoryService{
	private ICategoryRepository categoryRepository;
	private Gson gson;

	public CategoryService(ICategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
		GsonBuilder builder = new GsonBuilder();
		builder.addSerializationExclusionStrategy(new ExclusionStrategy() {
			
			@Override
			public boolean shouldSkipField(FieldAttributes f) {
				return f.getDeclaringClass() == Category.class && f.getName().equals("films");
			}
			
			@Override
			public boolean shouldSkipClass(Class<?> clazz) {
				return false;
			}
		});
		gson = builder.create();
	}
	
	public String getAllCategoriesAsJson() {
		List<Category> categories = categoryRepository.findAll();
		return gson.toJson(categories);
	}
	
}