package com.godofwibu.narga.repositories;

import org.hibernate.SessionFactory;

import com.godofwibu.narga.entities.Ticket;

public class TicketRepository extends CurdRepository<Ticket, Integer> implements ITicketRepository {

	public TicketRepository(SessionFactory sessionFactory) {
		super(sessionFactory, Ticket.class);
	}
	
}
