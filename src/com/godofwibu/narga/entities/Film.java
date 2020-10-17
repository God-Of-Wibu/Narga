package com.godofwibu.narga.entities;

import java.util.HashSet;
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

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;

import com.google.gson.annotations.Expose;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Indexed
@Entity
@Table(name = "film")
public class Film {
	@Expose
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Expose
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Column(name = "title", unique = true)
	private String title;
	
	@Expose
	@Column(name = "description")
	private String description;
	
	@Expose
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "poster")
	private ImageData poster;
	
	@Expose
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(
			name = "film_category",
			joinColumns = { @JoinColumn(name = "film_id") },
			inverseJoinColumns = { @JoinColumn(name = "category_id") }
	)
	private Set<Category> categories = new HashSet<Category>();
	
	@Expose
	@ManyToMany(fetch = FetchType.EAGER)
	@IndexedEmbedded
	@JoinTable(
			name = "casting",
			joinColumns = { @JoinColumn(name = "film_id") },
			inverseJoinColumns = { @JoinColumn(name = "actor_id") }
	)
	private Set<Actor> casting = new HashSet<Actor>();
	
	@Expose
	@Column(name = "running_time")
	private Integer runningTime;
	
	@Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES)
	@DateBridge(resolution = Resolution.YEAR)
	@Expose
	@Column(name = "release_date")
	private java.util.Date releaseDate;
	
	@Expose
	@IndexedEmbedded
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;
	
	@IndexedEmbedded
	@Expose
	@ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "director_id")
	private Director director;
}
