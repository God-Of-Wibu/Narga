package com.godofwibu.narga.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Indexed
@NoArgsConstructor
@Data
@Entity
@Table(name = "actor")
public class Actor {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Field(analyze = Analyze.YES, index = Index.YES, store = Store.NO)
	@Column(name = "name")
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;
	
	@Column(name = "age")
	private Integer age;
	
	@ManyToOne
	@JoinColumn(name = "avatar", referencedColumnName = "id")
	private ImageData avatar;
	
	@ManyToOne
	@JoinColumn(name = "contry_id", referencedColumnName = "id")
	private Country country;
}
