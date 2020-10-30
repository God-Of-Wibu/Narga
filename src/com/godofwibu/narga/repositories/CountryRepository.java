package com.godofwibu.narga.repositories;

import org.hibernate.SessionFactory;

import com.godofwibu.narga.entities.Country;

public class CountryRepository extends CurdRepository<Country, String> implements ICountryRepository {

	public CountryRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Country.class);
	}

	@Override
	public Country findByName(String name) {
		return getSession()
				.createQuery("SELECT c FROM Country AS c WHERE c.name=:name", Country.class)
				.setParameter("name", name)
				.uniqueResult();
	}
}
