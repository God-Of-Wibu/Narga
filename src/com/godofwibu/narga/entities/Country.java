package com.godofwibu.narga.entities;

import java.util.List;
import java.util.Set;

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

@NoArgsConstructor
@Data
@Entity
@Table(name = "country")
public class Country {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "name", unique = true)
	private String name;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "flag")
	private ImageData flag;
	
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<Film> films;
	
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<Actor> actors; 
	
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<Director> directors;

	public Country(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}
