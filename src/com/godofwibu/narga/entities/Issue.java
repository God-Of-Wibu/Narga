package com.godofwibu.narga.entities;

import java.sql.Date;
import java.sql.Time;
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
@Table(name = "issue")
public class Issue {
	
	@Id
	@Column(name = "id")
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Film film;
	
	@Column
	private Time time;
	
	@Column
	private Date date;
	
	@OneToMany(mappedBy = "issue", fetch = FetchType.LAZY)
	private List<Ticket> tickets;
	
}
