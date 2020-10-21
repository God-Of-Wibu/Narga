package com.godofwibu.narga.services;

import java.util.List;

import com.godofwibu.narga.entities.Country;
import com.godofwibu.narga.repositories.ICountryRepository;

public class CountryService implements ICountryService {

	private ICountryRepository countryRepository;
	private TransactionalOperationExecutor operationExecutor;

	public CountryService(ICountryRepository countryRepository, TransactionalOperationExecutor operationExecutor) {
		super();
		this.countryRepository = countryRepository;
		this.operationExecutor = operationExecutor;
	}

	@Override
	public List<Country> getAllCountries() throws ServiceLayerException {
		return operationExecutor.execute(() -> countryRepository.findAll());
	}

}
