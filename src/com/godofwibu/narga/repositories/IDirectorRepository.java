package com.godofwibu.narga.repositories;

import java.util.List;

import com.godofwibu.narga.entities.Director;

public interface IDirectorRepository extends ICurdRepository<Director, Integer> {
	List<Director> search(String input);
}
