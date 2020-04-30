package com.chernova.folderstructure.service.impl;

import com.chernova.folderstructure.model.File;
import com.chernova.folderstructure.repo.FileRepository;
import com.chernova.folderstructure.service.FileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DefaultFileService implements FileService {

	@Resource
	private FileRepository fileRepository;

	@Override
	public File save(File file) {
		return fileRepository.save(file);
	}

	@Override
	public File getFileById(Long fileId) {
		return fileRepository.getFileByFileId(fileId);
	}

	@Override
	public File update(File file) {
		return fileRepository.save(file);
	}

	@Override
	public void delete(Long fileId) {
		fileRepository.deleteById(fileId);
	}
}
