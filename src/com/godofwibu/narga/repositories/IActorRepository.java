package com.godofwibu.narga.repositories;

import java.util.List;

import com.godofwibu.narga.entities.Actor;

public interface IActorRepository extends ICurdRepository<Actor, Integer>{
	List<Actor> search(String input);
}
