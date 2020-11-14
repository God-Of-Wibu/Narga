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
	public List<Issue> getIssueByDateBetween(Date begin, Date end) {
		return getSession().createQuery("FROM Issue As _issue WHERE _issue.date BETWEEN :beginDate AND :endDate", Issue.class)
				.setParameter("beginDate", begin)
				.setParameter("endDate", end)
				.getResultList();
	}

}
