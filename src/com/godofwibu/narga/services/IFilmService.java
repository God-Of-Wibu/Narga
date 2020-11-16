package com.godofwibu.narga.services;

import java.util.List;

import com.godofwibu.narga.dto.Film_DTO_Home;
import com.godofwibu.narga.entities.Film;
import com.godofwibu.narga.formdata.AddFilmFormData;

public interface IFilmService {
	void addNewFilm(AddFilmFormData filmFormData) throws ServiceLayerException;
	String getAllFilmAsJson() throws ServiceLayerException;
	String searchFilmAsJson(String input) throws ServiceLayerException;
	Film getFilmDetail(int filmId) throws ServiceLayerException;
	List<Film_DTO_Home> getFilmInWeek() throws ServiceLayerException;
	List<Film_DTO_Home> getHotFilms() throws ServiceLayerException;
	Film getFilm(int filmId) throws ServiceLayerException;
}
