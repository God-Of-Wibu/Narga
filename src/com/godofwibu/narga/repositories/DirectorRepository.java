package com.godofwibu.narga.repositories;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.exception.EmptyQueryException;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.godofwibu.narga.entities.Director;

public class DirectorRepository extends CrudRepository<Director, Integer> implements IDirectorRepository {
	

	public DirectorRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Director.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Director> search(String input) {
		FullTextSession fullTextSession = Search.getFullTextSession(getSession());
		List<Director> searhResult = null;
		try {
			QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
					.buildQueryBuilder()
					.forEntity(Director.class)
					.get();
			org.apache.lucene.search.Query luceneQuery = queryBuilder
					.keyword()
					.onField("name")
					.matching(input)
					.createQuery();
			javax.persistence.Query hibernateQuery = fullTextSession.createFullTextQuery(luceneQuery, Director.class);
			
			searhResult = hibernateQuery
					.setMaxResults(10)
					.getResultList();
			return searhResult;
		}catch (EmptyQueryException e) {
			return new ArrayList<Director>();
		}
	}	
}
