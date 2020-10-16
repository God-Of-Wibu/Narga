package com.godofwibu.narga.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "film")
public class Film {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "poster")
	private ImageData poster;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "film_category",
			joinColumns = { @JoinColumn(name = "film_id") },
			inverseJoinColumns = { @JoinColumn(name = "category_id") }
	)
	private Set<Category> categories = new HashSet<Category>();
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "casting",
			joinColumns = { @JoinColumn(name = "film_id") },
			inverseJoinColumns = { @JoinColumn(name = "actor_id") }
	)
	private Set<Actor> cast = new HashSet<Actor>();
	
	@Column(name = "running_time")
	private Integer runningTime;
	
	@Column(name = "release_date")
	private Date releaseDate;
	
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;
	
	@ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "director_id")
	private Director director;
}
