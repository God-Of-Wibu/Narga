package com.godofwibu.narga.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.godofwibu.narga.entities.ImageData;
import com.godofwibu.narga.repositories.IImageDataRepository;


public class ImageStorageService implements IImageStorageService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ImageStorageService.class);
	private final static int BUFF_SIZE = 1024 * 100;
	private File rootDirectory;
	private IImageDataRepository imageDataRepository;
	private String contextPath;
	private TransactionalOperationExecutor operationExecutor;
	
	public ImageStorageService(String contextPath, String path, IImageDataRepository imageDataRepository, TransactionalOperationExecutor operationExecutor) throws IOException {
		this.rootDirectory = new File(path);
		if (rootDirectory.exists()) {
			FileUtils.deleteDirectory(rootDirectory);
			
		}
		rootDirectory.mkdir();
		this.imageDataRepository = imageDataRepository;
		this.contextPath = contextPath;
		this.operationExecutor = operationExecutor;
	}
	
	@Override
	public ImageData saveImage(InputStream inputStream, String filename) throws ServiceLayerException {
		try (
				inputStream;
				OutputStream outputStream = new FileOutputStream(new File(rootDirectory, filename)); 
		) {
			System.out.println(new File(rootDirectory, filename).getAbsolutePath());
			byte[] buffer = new byte[BUFF_SIZE];
			int read = -1;
			while ((read = inputStream.read(buffer,0, BUFF_SIZE)) != -1) {
				outputStream.write(buffer, 0, read);
			}
			Integer id = imageDataRepository.insert(new ImageData(contextPath + "/" + "file" + "/" + filename, filename));
			ImageData imageData = imageDataRepository.findById(id);
			LOGGER.info("Saved image: {}", imageData);
			return imageData;
		} catch (IOException e) {
			LOGGER.error("Failed to save image: {}" + e.getMessage());
			throw new ServiceLayerException(e.getMessage(), e);
		}
	}

	@Override
	public void deleteImage(ImageData imageData) {
		if (imageData != null && imageData.getFileLocation() != null) {
			File f = new File(rootDirectory, imageData.getFileLocation());
			f.delete();
		}
		imageDataRepository.delete(imageData);
	}

	@Override
	public ImageData saveImage(String url) {
		Integer id = imageDataRepository.insert(new ImageData(url, null));
		return imageDataRepository.findById(id);
	}
}
