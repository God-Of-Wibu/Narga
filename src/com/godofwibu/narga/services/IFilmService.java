package com.godofwibu.narga.services;

import java.util.List;

import com.godofwibu.narga.dto.FilmResumeHome;
import com.godofwibu.narga.entities.Film;
import com.godofwibu.narga.formdata.AddFilmFormData;
import com.godofwibu.narga.services.exception.ServiceLayerException;

public interface IFilmService {
	void addNewFilm(AddFilmFormData filmFormData) throws ServiceLayerException;
	String getAllFilmAsJson() throws ServiceLayerException;
	String searchFilmAsJson(String input) throws ServiceLayerException;
	Film getFilmDetail(int filmId) throws ServiceLayerException;
	List<FilmResumeHome> getFilmInThisWeek() throws ServiceLayerException;
	List<FilmResumeHome> getHotFilms() throws ServiceLayerException;
	Film getFilm(int filmId) throws ServiceLayerException;
}
