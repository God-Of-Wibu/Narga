package com.godofwibu.narga.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "director")
public class Director {
	@Id
	@Column(name = "id")
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;
	
	@ManyToOne
	@JoinColumn(name = "avatar")
	private ImageData avatar;
	
	@ManyToOne
	@JoinColumn(name = "contry_id")
	private Country country;
	
	@Column(name = "age")
	private Integer age;
	
	@OneToMany(mappedBy = "director")
	private List<Film> films;
}
