package com.godofwibu.narga.services;

import java.io.IOException;

import javax.servlet.http.Part;

import com.godofwibu.narga.entities.Gender;

public interface IActorService {
	void addNewActor(String name, int age, Gender gender, String countryName, Part avatarPart) throws IOException;
}
