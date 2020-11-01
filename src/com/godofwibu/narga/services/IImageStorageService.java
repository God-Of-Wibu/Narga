package com.godofwibu.narga.services;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Part;

import com.godofwibu.narga.entities.ImageData;

public interface IImageStorageService {
	ImageData saveImage(InputStream inputStream, String filename) throws ServiceLayerException;
	ImageData saveImage(String url);
	void deleteImage(ImageData imageData);
	ImageData saveImage(Part part, String nameImage) throws ServiceLayerException,IOException;
}
