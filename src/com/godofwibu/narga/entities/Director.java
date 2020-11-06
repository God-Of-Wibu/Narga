package com.godofwibu.narga.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

@NoArgsConstructor
@Data
@Indexed
@Entity
@Table(name = "director")
public class Director {

	@Expose
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
	@Analyzer(definition = "customAnalayzer")
	@Column(name = "name", unique = true)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;
	
	@Expose
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "avatar")
	private ImageData avatar;
	
	@IndexedEmbedded
	@Expose
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "contry_id")
	private Country country;
	
	@Expose
	@Column(name = "age")
	private Integer age;
	
	@OneToMany(mappedBy = "director", fetch = FetchType.LAZY)
	private List<Film> films;
}
