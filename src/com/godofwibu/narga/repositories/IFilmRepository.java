package com.godofwibu.narga.repositories;


import java.sql.Date;
import java.util.List;

import com.godofwibu.narga.entities.Film;

public interface IFilmRepository extends ICurdRepository<Film, Integer>{
	List<Film> search(String input, int maxResult);
	List<Film> findHasIssueBetween(Date begin, Date end);
}
