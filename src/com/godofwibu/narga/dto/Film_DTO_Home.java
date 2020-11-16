package com.godofwibu.narga.dto;

public class Film_DTO_Home {
	private int id;
	private String poster;
	
	public Film_DTO_Home(int id, String poster) {
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
