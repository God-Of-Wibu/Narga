package com.godofwibu.narga.repositories;

import java.util.List;

import com.godofwibu.narga.entities.Ticket;

public interface ITicketRepository extends ICurdRepository<Ticket, Integer>{
	List<Ticket> findByIssueIdOrderByPositionDesc(int issueId);
}
