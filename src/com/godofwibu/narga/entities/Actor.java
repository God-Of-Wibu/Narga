package com.godofwibu.narga.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

import com.google.gson.annotations.Expose;

import lombok.Data;
import lombok.NoArgsConstructor;

@Indexed
@NoArgsConstructor
@Data
@Entity
@Table(name = "actor")
public class Actor {

	@Expose
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Field(analyze = Analyze.YES, index = Index.YES, store = Store.NO)
	@Analyzer(definition = "customAnalayzer")
	@Expose
	@Column(name = "name", unique = true, columnDefinition = "nvarchar(100)")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Expose
	@Column(name = "gender")
	private Gender gender;
	
	@Field(analyze = Analyze.YES, index = Index.YES, store = Store.NO)
	@Analyzer(definition = "customAnalayzer")
	@Column(name = "age")
	@Expose
	private Integer age;
	
	@Expose
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "avatar", referencedColumnName = "id")
	private ImageData avatar;
	
	@IndexedEmbedded
	@Expose
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contry_id", referencedColumnName = "id")
	private Country country;
	
	@ManyToMany
	private Set<Film> films;
}
