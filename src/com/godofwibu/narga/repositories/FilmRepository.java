	package com.godofwibu.narga.repositories;


import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import org.apache.lucene.search.Query;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.exception.EmptyQueryException;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.godofwibu.narga.entities.Film;

public class FilmRepository extends CrudRepository<Film, Integer> implements IFilmRepository {

	public FilmRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Film.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Film> search(String input, int maxResult) {
		FullTextSession fullTextSession = Search.getFullTextSession(getSession());
		List<Film> searchResult = null;
		try {
			QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
					.buildQueryBuilder()
					.forEntity(Film.class)
					.get();
			Query luceneQuery = queryBuilder
					.keyword()
					.onFields("title", "casting.name", "director.name", "country.name")
					.matching(input)
					.createQuery();
			javax.persistence.Query jpaQuery = fullTextSession.createFullTextQuery(luceneQuery, Film.class);
			searchResult = jpaQuery
					.setMaxResults(maxResult)
					.getResultList();
			return searchResult;
		} catch (EmptyQueryException e) {
			return new ArrayList<Film>();
		}
	}

	@Override
	public List<Film> findHasIssueBetween(Date begin, Date end) {
		return getSession().createQuery("SELECT DISTINCT film_ FROM Issue AS issue_ INNER JOIN issue_.film as film_ WHERE issue_.date BETWEEN :begin AND :end", Film.class)
				.setParameter("begin", begin)
				.setParameter("end", end)
				.getResultList();
	}

}
