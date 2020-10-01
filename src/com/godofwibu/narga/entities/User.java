package com.godofwibu.narga.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "user_id")
	private String id;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "personal_id")
	private String personalId;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "username")
	private String name;
	
	@OneToMany(mappedBy = "role_id", fetch = FetchType.LAZY)
	private Set<Role> roles;
}
