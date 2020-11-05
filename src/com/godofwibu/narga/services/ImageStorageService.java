package com.godofwibu.narga.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.godofwibu.narga.entities.ImageData;
import com.godofwibu.narga.repositories.IImageDataRepository;
import com.godofwibu.narga.utils.ITransactionTemplate;

public class ImageStorageService implements IImageStorageService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ImageStorageService.class);
	private final static int BUFF_SIZE = 1024 * 100;
	private File rootDirectory;
	private IImageDataRepository imageDataRepository;
	private String contextPath;
	private ITransactionTemplate transactionTemplate;
	private String fileResourcePrefix;

	public ImageStorageService(String contextPath, String rootDiretoryPath, String fileResourcePrefix,
			IImageDataRepository imageDataRepository, ITransactionTemplate transactionTemplate, boolean clearOnStartup) throws IOException {
		this.rootDirectory = new File(rootDiretoryPath);
		if (clearOnStartup && this.rootDirectory.exists()) {
			FileUtils.deleteDirectory(rootDirectory);
		}
		if (!this.rootDirectory.exists()) {
			this.rootDirectory.mkdir();
		}
		this.imageDataRepository = imageDataRepository;
		this.contextPath = contextPath;
		this.transactionTemplate = transactionTemplate;
		this.fileResourcePrefix = fileResourcePrefix;
	}

	@Override
	public ImageData saveImage(InputStream inputStream, String fileName) throws ServiceLayerException {
		return transactionTemplate.execute(() -> {
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
		transactionTemplate.execute(() -> {                                                                               
			if (imageData != null && imageData.getFileLocation() != null) {
				File f = new File(rootDirectory, imageData.getFileLocation());
				f.delete();
			}
			imageDataRepository.delete(imageData);
		});
	}

	@Override
	public ImageData saveImage(String url) {
		return transactionTemplate.execute(() -> {
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


	@Override
	public ImageData saveImage(Part part, String nameImage) throws ServiceLayerException, IOException {
		return saveImage(part.getInputStream(), nameImage);
	
	}
}
