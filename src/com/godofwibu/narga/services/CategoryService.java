package com.godofwibu.narga.services;

import com.godofwibu.narga.repositories.ICategoryRepository;
import com.godofwibu.narga.utils.ITransactionTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CategoryService implements ICategoryService {
	private ICategoryRepository categoryRepository;
	private Gson gson;
	private ITransactionTemplate executionWraper;

	public CategoryService(ICategoryRepository categoryRepository, ITransactionTemplate transactionTemplate) {
		this.categoryRepository = categoryRepository;
		this.executionWraper = transactionTemplate;
		gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.create();
	}
	public String getAllCategoriesAsJson() throws ServiceLayerException {
		return executionWraper.execute(() -> gson.toJson(categoryRepository.findAll()));
	}
}