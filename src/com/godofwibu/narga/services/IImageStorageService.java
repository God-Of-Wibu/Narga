package com.godofwibu.narga.services;

import java.io.IOException;
import java.io.InputStream;

import com.godofwibu.narga.entities.ImageData;

public interface IImageStorageService {
	ImageData saveImage(InputStream inputStream, String filename) throws IOException;
	ImageData saveImage(String url);
	void deleteImage(ImageData imageData);
}
