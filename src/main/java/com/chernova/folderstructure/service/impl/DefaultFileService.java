package com.chernova.folderstructure.service.impl;

import com.chernova.folderstructure.dto.FilePathDto;
import com.chernova.folderstructure.exception.CannotCreateFileInNonExistingFolderException;
import com.chernova.folderstructure.exception.CannotMoveFileException;
import com.chernova.folderstructure.exception.FileAlreadyExistsInFolderException;
import com.chernova.folderstructure.exception.FileNotFoundException;
import com.chernova.folderstructure.exception.FolderNotFoundException;
import com.chernova.folderstructure.model.File;
import com.chernova.folderstructure.model.Folder;
import com.chernova.folderstructure.repo.FileRepository;
import com.chernova.folderstructure.service.FileService;
import com.chernova.folderstructure.service.FolderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static java.util.Objects.isNull;

@Service
public class DefaultFileService implements FileService {

	private static final String FILE_ALREADY_EXISTS_EXCEPTION = "File with such name already exists in directory ";
	private static final String FILE_NOT_FOUND_EXCEPTION = "File not found with id: ";
	private static final String CANNOT_MOVE_FILE_EXCEPTION = "Cannot move file to such folder";
	private static final String CANNOT_CREATE_FILE_IN_NON_EXISTING_FOLDER_EXCEPTION =
			"Cannot create file in non existing folder";
	@Resource
	private FileRepository fileRepository;
	@Resource
	private FolderService folderService;

	@Override
	public File save(File file) throws FileAlreadyExistsInFolderException, FolderNotFoundException,
			CannotCreateFileInNonExistingFolderException {
		if (isNull(file.getFolderId())) {
			throw new CannotCreateFileInNonExistingFolderException(CANNOT_CREATE_FILE_IN_NON_EXISTING_FOLDER_EXCEPTION);
		}
		Folder folder = folderService.getFolderById(file.getFolderId());
		if (isPresentInFolderFileWithName(folder, file.getFileName())) {
			throw new FileAlreadyExistsInFolderException(FILE_ALREADY_EXISTS_EXCEPTION + folder.getFolderName());
		}
		file.setFolder(folder);
		return fileRepository.save(file);
	}

	@Override
	public File getFileById(Long fileId) throws FileNotFoundException {
		return fileRepository.findById(fileId)
				.orElseThrow(() -> new FileNotFoundException(FILE_NOT_FOUND_EXCEPTION + fileId));
	}

	@Override
	public File update(File file, Long fileId) throws FolderNotFoundException,
			FileAlreadyExistsInFolderException, FileNotFoundException, CannotMoveFileException {
		checkMovingFileToNotExistingFolder(file);
		checkIfFileWithIdExists(fileId);
		checkIfFileNotExistsInFolder(file);
		file.setFileId(fileId);
		Folder foundFolder = folderService.getFolderById(file.getFolderId());
		file.setFolder(foundFolder);
		return fileRepository.save(file);
	}

	private void checkMovingFileToNotExistingFolder(File file) throws CannotMoveFileException {
		if (isNull(file.getFolderId())) {
			throw new CannotMoveFileException(CANNOT_MOVE_FILE_EXCEPTION);
		}
	}

	private void checkIfFileNotExistsInFolder(File file) throws FolderNotFoundException,
			FileAlreadyExistsInFolderException {
		Folder foundFolder = folderService.getFolderById(file.getFolderId());
		if (isPresentInFolderFileWithName(foundFolder, file.getFileName())) {
			throw new FileAlreadyExistsInFolderException(FILE_ALREADY_EXISTS_EXCEPTION + foundFolder.getFolderName());
		}
	}

	private void checkIfFileWithIdExists(Long fileId) throws FileNotFoundException {
		if (fileRepository.findById(fileId).isEmpty()) {
			throw new FileNotFoundException(FILE_NOT_FOUND_EXCEPTION + fileId);
		}
	}

	@Override
	public void delete(Long fileId) throws FileNotFoundException {
		fileRepository.delete(getFileById(fileId));
	}

	@Override
	public boolean isPresentInFolderFileWithName(Folder folder, String fileName) {
		return fileRepository.getFileByFolderAndFileName(folder, fileName).isPresent();
	}

	@Override
	public FilePathDto getFullFilePath(File file) {
		Folder folder = file.getFolder();
		String folderName = folder.getFolderName();
		String fileName = file.getFileName();
		String path = (folderService.isRoot(folder))
				? (folderName + '/' + fileName)
				: (fileRepository.getFullFilePath(folderName) + '/' + fileName);
		return new FilePathDto(path);
	}

}
