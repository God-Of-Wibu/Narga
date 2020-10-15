package com.godofwibu.narga.entities;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "profile")
public class Profile {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

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

	public Profile(String personalId, String phoneNumber, String email, String name, ImageData avatar, Gender gender) {
		super();
		this.personalId = personalId;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.name = name;
		this.avatar = avatar;
		this.gender = gender;
	}

	public Profile(String personalId, String name, Gender gender) {
		super();
		this.personalId = personalId;
		this.name = name;
		this.gender = gender;
	}
	
}
