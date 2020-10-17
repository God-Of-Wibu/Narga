package com.godofwibu.narga.services;

import javax.servlet.http.Part;

import com.godofwibu.narga.entities.Gender;

public interface IDirectorService {

	void addNewDirector(String name, int age, Gender gender, String countryName, Part avatarPart);

}
