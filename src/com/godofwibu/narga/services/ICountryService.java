package com.godofwibu.narga.services;

import java.util.List;

import com.godofwibu.narga.entities.Country;
import com.godofwibu.narga.services.exception.ServiceLayerException;

public interface ICountryService {
	List<Country> getAllCountries() throws ServiceLayerException;
}
