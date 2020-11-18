package com.godofwibu.narga.repositories;

import java.sql.Date;
import java.util.List;

import org.hibernate.SessionFactory;

import com.godofwibu.narga.entities.Issue;

public class IssueRepository extends CrudRepository<Issue, Integer> implements  IIssueRepository {

	public IssueRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Issue.class);
	}

	@Override
	public List<Issue> findByDateBetween(Date begin, Date end) {
		return getSession().createQuery("FROM Issue AS _issue WHERE _issue.date BETWEEN :beginDate AND :endDate", Issue.class)
				.setParameter("beginDate", begin)
				.setParameter("endDate", end)
				.getResultList();
	}

	@Override
	public List<Issue> findByFilmIdAndDate(int filmId, Date date) {
		return getSession().createQuery("FROM Issue AS _issue WHERE _issue.date=:date AND _issue.film.id=:filmId", Issue.class)
				.setParameter("date", date)
				.setParameter("filmId", filmId)
				.getResultList();
	}

}
