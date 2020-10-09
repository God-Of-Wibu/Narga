package com.godofwibu.narga.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Actor {

	@Id
	@Column(name = "id")
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;
	
	@Column(name = "age")
	private Integer age;
	
	@ManyToOne
	@JoinColumn(name = "avatar")
	private ImageData avatar;
	
	@ManyToOne
	@JoinColumn(name = "contry_id")
	private Country country;
}
