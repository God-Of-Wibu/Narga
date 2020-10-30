package com.godofwibu.narga.services;

import java.util.List;

import com.godofwibu.narga.entities.Country;
import com.godofwibu.narga.repositories.ICountryRepository;
import com.godofwibu.narga.repositories.IDbOperationExecutionWrapper;

public class CountryService implements ICountryService {

	private ICountryRepository countryRepository;
	private IDbOperationExecutionWrapper dbOperationExecutionWrapper;


	public CountryService(ICountryRepository countryRepository, IDbOperationExecutionWrapper dbOperationExecutionWrapper) {
		super();
		this.countryRepository = countryRepository;
		this.dbOperationExecutionWrapper = dbOperationExecutionWrapper;
	}



	@Override
	public List<Country> getAllCountries() throws ServiceLayerException {
		return dbOperationExecutionWrapper.execute(() -> countryRepository.findAll());
	}

}
