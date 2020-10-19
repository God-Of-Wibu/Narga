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

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.google.gson.annotations.Expose;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Indexed
@Table(name = "country")
public class Country {
	
	@Expose
	@Id
	@Column(name = "id")
	private String id;
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Analyzer(definition = "customAnalayzer")
	@Expose
	@Column(name = "name", unique = true)
	private String name;
	
	@Expose
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
