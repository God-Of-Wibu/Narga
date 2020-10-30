package com.godofwibu.narga.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.godofwibu.narga.entities.ImageData;
import com.godofwibu.narga.repositories.IDbOperationExecutionWrapper;
import com.godofwibu.narga.repositories.IImageDataRepository;

public class ImageStorageService implements IImageStorageService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ImageStorageService.class);
	private final static int BUFF_SIZE = 1024 * 100;
	private File rootDirectory;
	private IImageDataRepository imageDataRepository;
	private String contextPath;
	private IDbOperationExecutionWrapper dbOperationExecutionWrapper;
	private String fileResourcePrefix;

	public ImageStorageService(String contextPath, String rootDiretoryPath, String fileResourcePrefix,
			IImageDataRepository imageDataRepository, IDbOperationExecutionWrapper dbOperationExecutionWrapper) throws IOException {
		this.rootDirectory = new File(rootDiretoryPath);
		if (!rootDirectory.exists())
			rootDirectory.mkdir();
		this.imageDataRepository = imageDataRepository;
		this.contextPath = contextPath;
		this.dbOperationExecutionWrapper = dbOperationExecutionWrapper;
		this.fileResourcePrefix = fileResourcePrefix;
	}

	@Override
	public ImageData saveImage(InputStream inputStream, String fileName) throws ServiceLayerException {
		return dbOperationExecutionWrapper.execute(() -> {
			try  {
				writeToFile(inputStream, fileName);
				String url = contextPath + fileResourcePrefix + "/" + fileName;
				Integer id = imageDataRepository.insert(new ImageData(url, fileName));
				ImageData imageData = imageDataRepository.findById(id);
				return imageData;
			} catch (IOException e) {
				throw new ServiceLayerException("Unable to write image to file", e);
			}
		});
	}

	@Override
	public void deleteImage(ImageData imageData) {
		dbOperationExecutionWrapper.execute(() -> {                                                                               
			if (imageData != null && imageData.getFileLocation() != null) {
				File f = new File(rootDirectory, imageData.getFileLocation());
				f.delete();
			}
			imageDataRepository.delete(imageData);
		});
	}

	@Override
	public ImageData saveImage(String url) {
		return dbOperationExecutionWrapper.execute(() -> {
			Integer id = imageDataRepository.insert(new ImageData(url, null));
			return imageDataRepository.findById(id);
		});
	}

	private void writeToFile(InputStream inputStream, String fileName) throws IOException {
		try (inputStream; OutputStream outputStream = new FileOutputStream(new File(rootDirectory, fileName));) {
			byte[] buffer = new byte[BUFF_SIZE];
			int read = -1;
			while ((read = inputStream.read(buffer, 0, BUFF_SIZE)) != -1) {
				outputStream.write(buffer, 0, read);
			}
		}
	}
}
