package com.neptunebank.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class FileUploadService {

	private final Logger log = LoggerFactory.getLogger(FileUploadService.class);

	private final static String MEDIA_FOLDER = new File(".").getAbsolutePath() + "/mediaResources/";

	public List<File> getMediaFileList() {
		return Arrays.asList(new File(MEDIA_FOLDER).listFiles(file -> file.isFile() && !file.getName().startsWith(".")));
	}

	public File getMediaFile(String fileName) {
		File file = new File(MEDIA_FOLDER + fileName);

		if (file.exists() && file.canRead() && file.isFile()) {
			return file;
		}

		return null;
	}

	public void saveMediaFile(MultipartFile multiPartFile) throws IOException {
		File file = new File(MEDIA_FOLDER + multiPartFile.getOriginalFilename());

		multiPartFile.transferTo(file);
	}

	public boolean deleteMediaFile(String fileName) {
		File file = new File(MEDIA_FOLDER + fileName);

		return file.exists() && file.canWrite() && file.delete();
	}
}
