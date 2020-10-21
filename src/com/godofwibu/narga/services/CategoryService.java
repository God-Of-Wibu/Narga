package com.godofwibu.narga.services;

import com.godofwibu.narga.repositories.ICategoryRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CategoryService implements ICategoryService {
	private ICategoryRepository categoryRepository;
	private Gson gson;
	private TransactionalOperationExecutor operationExecutor;

	public CategoryService(ICategoryRepository categoryRepository, TransactionalOperationExecutor operationExecutor) {
		this.categoryRepository = categoryRepository;
		this.operationExecutor = operationExecutor;
		gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.create();
	}
	public String getAllCategoriesAsJson() throws ServiceLayerException {
		return operationExecutor.execute(() -> gson.toJson(categoryRepository.findAll()));
	}
}