package com.godofwibu.narga.services;

import javax.servlet.http.Part;

public interface IFilmService {
	void addNewFilm(String title, Part poster, String country, String director, int runningTime, String[] categories, String[] casting) throws ServiceLayerException;
	String getAllFilmAsJson() throws ServiceLayerException;
	String searchFilmAsJson(String input) throws ServiceLayerException;
}
