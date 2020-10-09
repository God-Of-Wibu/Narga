package com.godofwibu.narga.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "profile")
public class Profile {
	
	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "personal_id")
	private String personalId;

	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "avatar")
	private ImageData avatar;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender", length = 10)
	private Gender gender;
}
