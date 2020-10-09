package com.godofwibu.narga.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "country")
public class Country {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<Film> films;
	
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<Actor> actors; 
	
	@OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
	private List<Director> directors;
	
}
