package com.godofwibu.narga.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ticket_type")
public class TicketType {
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "film")
	private Film film;
	
	
	@Column(name = "price", nullable = false)
	private Integer price;
	
	@Column(name = "availiable", nullable = false)
	private Integer availiable;
	
	@OneToMany(mappedBy = "ticketType", fetch = FetchType.LAZY)
	private List<Ticket> tickets;
	
}
