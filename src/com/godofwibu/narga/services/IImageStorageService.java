package com.godofwibu.narga.services;

import java.io.InputStream;

import javax.servlet.http.Part;

import com.godofwibu.narga.entities.ImageData;
import com.godofwibu.narga.services.exception.ServiceLayerException;

public interface IImageStorageService {
	ImageData saveImage(InputStream inputStream, String filename) throws ServiceLayerException;
	ImageData saveImage(String url) throws ServiceLayerException;
	void deleteImage(ImageData imageData) throws ServiceLayerException;
	ImageData saveImage(Part part, String nameImage) throws ServiceLayerException;
}
