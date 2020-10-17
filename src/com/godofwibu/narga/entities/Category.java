package com.godofwibu.narga.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity 
@Table(name = "category")
public class Category {
	
	@Expose
	@Id
	@Column(name = "name", nullable = false)
	private String name;
	
	@ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
	private List<Film> films;

	public Category(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category {name:" + name + "}";
	}
}
