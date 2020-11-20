package com.godofwibu.narga.dto;

import com.godofwibu.narga.entities.Film;

import lombok.Data;

@Data
public class FilmResume {
	private int id;
	private String title;
	private String description;
	private String poster;
	public FilmResume(Film film) {
		this.id = film.getId(); 
		this.title = film.getTitle();
		this.description = film.getDescription();
		this.poster = film.getPoster().getUrl();
	}
	

}
