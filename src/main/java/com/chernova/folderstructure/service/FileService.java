package com.chernova.folderstructure.service;

import com.chernova.folderstructure.dto.FilePathDto;
import com.chernova.folderstructure.exception.CannotCreateFileInNonExistingFolderException;
import com.chernova.folderstructure.exception.CannotMoveFileException;
import com.chernova.folderstructure.exception.FileAlreadyExistsInFolderException;
import com.chernova.folderstructure.exception.FileNotFoundException;
import com.chernova.folderstructure.exception.FolderNotFoundException;
import com.chernova.folderstructure.model.File;
import com.chernova.folderstructure.model.Folder;

public interface FileService {

	File save(File file) throws FileAlreadyExistsInFolderException, FolderNotFoundException,
			CannotCreateFileInNonExistingFolderException;

	File getFileById(Long fileId) throws FileNotFoundException;

	File update(File file, Long fileId) throws FolderNotFoundException,
			FileAlreadyExistsInFolderException, FileNotFoundException, CannotMoveFileException;

	void delete(Long fileId) throws FileNotFoundException;

	boolean isPresentInFolderFileWithName(Folder folder, String fileName) throws FileNotFoundException;

	FilePathDto getFullFilePath(File file);

}
