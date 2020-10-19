package com.godofwibu.narga.repositories;

import java.util.List;

import com.godofwibu.narga.entities.Actor;
import com.google.gson.JsonElement;

public interface IActorRepository extends ICurdRepository<Actor, Integer>{
	List<Actor> searchByName(String input, int maxResult);

	List<Actor> findFirst(int maxResult);
}
