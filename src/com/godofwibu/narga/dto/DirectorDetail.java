package com.godofwibu.narga.dto;

import java.util.HashSet;
import java.util.Set;


import com.godofwibu.narga.entities.Country;
import com.godofwibu.narga.entities.Director;
import com.godofwibu.narga.entities.Film;
import com.godofwibu.narga.entities.Gender;

import lombok.Data;
@Data
public class DirectorDetail {
	private String name;
	private String gender;
	private Integer age;
	private String avatar;
	private Country country;
	
	
	private Set<FilmMinimumDetail> films;
	public DirectorDetail(Director director) {
		this.name = director.getName();
		this.gender = director.getGender() == Gender.MALE ? "Nam":"Nữ";
		this.age = director.getAge();
		this.avatar = director.getAvatar().getUrl();
		this.country = director.getCountry();
		this.films = new HashSet<FilmMinimumDetail>();
		for(Film film : director.getFilms() ) {
			films.add(new FilmMinimumDetail(film));
		}
}
}
