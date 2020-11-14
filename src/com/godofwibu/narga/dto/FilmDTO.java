package com.godofwibu.narga.dto;

public class FilmDTO {
	private int id;
	private String poster;
	
	
	public FilmDTO(int id, String poster) {
		super();
		this.id = id;
		this.poster = poster;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
}
