package com.godofwibu.narga.services;

import java.util.List;

import com.godofwibu.narga.entities.Country;
import com.godofwibu.narga.repositories.ICountryRepository;
import com.godofwibu.narga.utils.ITransactionTemplate;

public class CountryService implements ICountryService {

	private ICountryRepository countryRepository;
	private ITransactionTemplate transactionTemplate;


	public CountryService(ICountryRepository countryRepository, ITransactionTemplate transactionTemplate) {
		super();
		this.countryRepository = countryRepository;
		this.transactionTemplate = transactionTemplate;
	}



	@Override
	public List<Country> getAllCountries() throws ServiceLayerException {
		return transactionTemplate.execute(() -> countryRepository.findAll());
	}

}
