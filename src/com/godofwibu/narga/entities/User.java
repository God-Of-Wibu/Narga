package com.godofwibu.narga.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "role", nullable = false)
	private Role role;
	
	@OneToOne
	@JoinColumn(name = "profile_id", nullable = false)
	@Cascade(CascadeType.ALL)
	private Profile profile;
}
