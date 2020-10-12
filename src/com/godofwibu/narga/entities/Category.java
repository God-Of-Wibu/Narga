package com.godofwibu.narga.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity 
@Table(name = "category")
public class Category {
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@OneToOne
	@JoinColumn(name = "flag")
	private ImageData flag;
	
	@ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
	private List<Film> films;

	public Category(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}
