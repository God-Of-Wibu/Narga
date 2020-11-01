package com.godofwibu.narga.services;

import com.godofwibu.narga.dto.AddFilmFormData;
import com.godofwibu.narga.entities.Film;

public interface IFilmService {
	void addNewFilm(AddFilmFormData filmFormData) throws ServiceLayerException;
	String getAllFilmAsJson() throws ServiceLayerException;
	String searchFilmAsJson(String input) throws ServiceLayerException;
	Film getFilmDetail(int filmId) throws ServiceLayerException;
}
