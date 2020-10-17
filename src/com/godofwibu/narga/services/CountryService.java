package com.godofwibu.narga.services;

import java.util.List;

import com.godofwibu.narga.entities.Country;
import com.godofwibu.narga.repositories.ICountryRepository;

public class CountryService implements ICountryService {
	
	private ICountryRepository countryRepository;

	public CountryService(ICountryRepository countryRepository) {
		super();
		this.countryRepository = countryRepository;
	}

	@Override
	public List<Country> getAllCoutries() {
		return countryRepository.findAll();
	}

}
