package com.godofwibu.narga.repositories;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.exception.EmptyQueryException;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.godofwibu.narga.entities.Actor;
import com.godofwibu.narga.entities.Director;

public class ActorRepository extends CurdRepository<Actor, Integer> implements IActorRepository {
	
	public ActorRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Actor.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Actor> searchByName(String input, int maxResult) {
		FullTextSession fullTextSession = Search.getFullTextSession(getSession());
		List<Actor> searhResult = null;
		try {
			QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
					.buildQueryBuilder()
					.forEntity(Director.class)
					.get();
			org.apache.lucene.search.Query luceneQuery = queryBuilder
					.keyword()
					.onFields("name", "country.name")
					.matching(input)
					.createQuery();
			javax.persistence.Query hibernateQuery = fullTextSession
					.createFullTextQuery(luceneQuery, Actor.class)
					.setMaxResults(maxResult);
			searhResult = hibernateQuery
					.getResultList();
			fullTextSession.close();
			return searhResult;
		} catch (EmptyQueryException e) {
			return new ArrayList<Actor>();
		}finally {
			fullTextSession.close();
		}
	}

	@Override
	public List<Actor> findFirst(int maxResult) {
			return  getSession()
					.createQuery("FROM Actor", Actor.class)
					.setMaxResults(maxResult)
					.getResultList();
	}	
}
