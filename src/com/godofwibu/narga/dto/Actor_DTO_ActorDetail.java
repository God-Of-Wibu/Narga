package com.godofwibu.narga.dto;


import java.util.HashSet;
import java.util.Set;

import com.godofwibu.narga.entities.Actor;
import com.godofwibu.narga.entities.Country;
import com.godofwibu.narga.entities.Film;
import com.godofwibu.narga.entities.Gender;

import lombok.Data;


@Data
public class Actor_DTO_ActorDetail {
	private String name;
	private String gender;
	private Integer age;
	private String avatar;
	private Country country;
	private Set<Film_DTO_ActorDetail> films;
	public Actor_DTO_ActorDetail(Actor actor) {
		this.name = actor.getName();
		this.gender = actor.getGender() == Gender.MALE ? "Nam":"Nữ";
		this.age = actor.getAge();
		this.avatar = actor.getAvatar().getUrl();
		this.country = actor.getCountry();
		this.films = new HashSet<Film_DTO_ActorDetail>();
		for(Film film : actor.getFilms() ) {
			films.add(new Film_DTO_ActorDetail(film));
		}
	}


	
	
	
	
	

}
