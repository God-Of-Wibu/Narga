package com.godofwibu.narga.services;

import javax.servlet.http.Part;

import com.godofwibu.narga.entities.Gender;

public interface IActorService {
	void addNewActor(String name, int age, Gender gender, String countryName, Part avatarPart) throws ServiceLayerException;

	String getAllActorsAsJson() throws ServiceLayerException;

	String searchActorAsJson(String input, int maxResult) throws ServiceLayerException;

	String getFirstActorsAsJson(int maxResult) throws ServiceLayerException;
}
