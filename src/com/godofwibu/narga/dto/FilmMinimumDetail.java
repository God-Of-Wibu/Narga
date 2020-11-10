package com.godofwibu.narga.dto;

import com.godofwibu.narga.entities.Film;

import lombok.Data;

@Data
public class FilmMinimumDetail {
	private String title;
	private String description;
	private String poster;
	public FilmMinimumDetail(Film film) {
		this.title = film.getTitle();
		this.description = film.getDescription();
		this.poster = film.getPoster().getUrl();
	}
	

}