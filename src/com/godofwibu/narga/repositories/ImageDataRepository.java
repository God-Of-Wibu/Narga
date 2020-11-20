package com.godofwibu.narga.repositories;

import org.hibernate.SessionFactory;

import com.godofwibu.narga.entities.ImageData;

public class ImageDataRepository extends CrudRepository<ImageData, Integer> implements IImageDataRepository {
	public ImageDataRepository(SessionFactory sessionFactory) {
		super(sessionFactory, ImageData.class);
	}
}
