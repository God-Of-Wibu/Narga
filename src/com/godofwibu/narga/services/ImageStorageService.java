package com.godofwibu.narga.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.godofwibu.narga.entities.ImageData;
import com.godofwibu.narga.repositories.IImageDataRepository;


public class ImageStorageService implements IImageStorageService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ImageStorageService.class);
	private final static int BUFF_SIZE = 1024 * 100;
	private byte[] buff;
	private File dir;
	private IImageDataRepository repo;
	
	public ImageStorageService(String path, IImageDataRepository repo) {
		this.dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		buff = new byte[BUFF_SIZE];
	}
	
	@Override
	public ImageData saveImage(InputStream inputStream, String filename) throws IOException {
		File f = new File(dir, filename);
		if (f.createNewFile()) {
			OutputStream os = new FileOutputStream(f);
			int byteRead = -1;
			while ((byteRead = inputStream.read(buff)) != -1) {
				os.write(buff, 0, byteRead);
			}
			os.close();
			LOGGER.debug("Save :" + filename);
			Integer id = repo.insert(new ImageData());
			
			return repo.findById(id);
		}
		inputStream.close();
		return null;
	}

	@Override
	public void deleteImage(ImageData imageData) {
		if (imageData.getLocalFile() != null) {
			File f = new File(dir, imageData.getLocalFile());
			f.delete();
		}
		repo.delete(imageData);
	}

}
