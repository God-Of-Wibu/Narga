package com.godofwibu.narga.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
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
	
	@ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
	private List<Film> films;
}
