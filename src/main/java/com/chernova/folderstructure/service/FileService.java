package com.chernova.folderstructure.service;

import com.chernova.folderstructure.model.File;

public interface FileService {

	File save(File file);

	File getFileById(Long fileId);

	File update(File file);

	void delete(Long fileId);

}
