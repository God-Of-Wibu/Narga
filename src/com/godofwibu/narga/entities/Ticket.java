package com.godofwibu.narga.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ticket")
public class Ticket {
	@Id
	@Column(name = "id")
	String id;

	@ManyToOne
	@JoinColumn(name = "ticket_type_id", nullable = false)
	TicketType ticketType;
	
	@OneToOne
	@JoinColumn(name = "owner")
	private User owner;
	
	@Column(name = "cost")
	private Integer cost;
}
