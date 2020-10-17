package com.godofwibu.narga.repositories;

import java.util.List;

import com.godofwibu.narga.entities.Film;

public interface IFilmRepository extends ICurdRepository<Film, Integer>{
	List<Film> search(String input);
}
