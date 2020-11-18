package com.godofwibu.narga.services;

import com.godofwibu.narga.services.exception.ServiceLayerException;

public interface ICategoryService {
	String getAllCategoriesAsJson() throws ServiceLayerException;
}
