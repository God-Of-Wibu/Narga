package com.godofwibu.narga.repositories;

import java.util.List;

import org.hibernate.SessionFactory;

import com.godofwibu.narga.entities.Ticket;

public class TicketRepository extends CrudRepository<Ticket, Integer> implements ITicketRepository {

	public TicketRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Ticket.class);
	}

	@Override
	public List<Ticket> findByIssueIdOrderByPositionDesc(int issueId) {
		return getSession()
				.createQuery("FROM Ticket ticket_ WHERE ticket_.issue.id=:issueId ORDER BY ticket_.position DESC", Ticket.class)
				.setParameter("issueId", issueId)
				.getResultList();
	}
	
}
