package com.chernova.folderstructure.service.impl;

import com.chernova.folderstructure.dto.FolderPathDto;
import com.chernova.folderstructure.exception.CannotCreateRootException;
import com.chernova.folderstructure.exception.CannotDeleteRootFolderException;
import com.chernova.folderstructure.exception.CannotMoveRootFolderException;
import com.chernova.folderstructure.exception.FolderAlreadyExistsException;
import com.chernova.folderstructure.exception.FolderNotFoundException;
import com.chernova.folderstructure.model.Folder;
import com.chernova.folderstructure.repo.FolderRepository;
import com.chernova.folderstructure.service.FolderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static java.util.Objects.isNull;

@Service
public class DefaultFolderService implements FolderService {

	private static final String FOLDER_NOT_FOUND_EXCEPTION_MESSAGE = "Folder not found";
	private static final String CANNOT_CREATE_ROOT_EXCEPTION_MESSAGE = "Cannot create new root";
	private static final String CANNOT_MOVE_ROOT_FOLDER_EXCEPTION_MESSAGE = "Can't move root to another folder";
	private static final String CANNOT_DELETE_ROOT_FOLDER_EXCEPTION_MESSAGE = "Cannot delete root";
	private static final String FOLDER_ALREADY_EXISTS_EXCEPTION_MESSAGE =
			"Folder with such name already exists in directory ";

	@Resource
	private FolderRepository folderRepository;

	@Override
	public Folder save(Folder folder) throws FolderAlreadyExistsException, CannotCreateRootException,
			FolderNotFoundException {
		checkIfCreateNewRootFolder(folder);
		Folder fatherFolder = getFolderById(folder.getFatherFolderId());
		if (isPresentInFatherFolderFolderWithName(fatherFolder, folder.getFolderName())) {
			throw new FolderAlreadyExistsException(FOLDER_ALREADY_EXISTS_EXCEPTION_MESSAGE + fatherFolder.getFolderName());
		}
		folder.setFatherFolder(fatherFolder);
		return folderRepository.save(folder);
	}

	@Override
	public Folder getFolderById(Long folderId) throws FolderNotFoundException {
		return folderRepository.findById(folderId)
				.orElseThrow(() -> new FolderNotFoundException(FOLDER_NOT_FOUND_EXCEPTION_MESSAGE));
	}

	@Override
	public Folder update(Folder folder, Long folderId) throws FolderAlreadyExistsException, FolderNotFoundException,
			CannotMoveRootFolderException, CannotCreateRootException {
		checkIfCreateNewRootFolder(folder);
		checkIfMoveRootFolder(folder);
		checkIfFolderWithIdExists(folderId);
		checkIfFolderNotExistsInFatherFolder(folder);
		folder.setFolderId(folderId);
		folder.setFatherFolder(getFolderById(folder.getFatherFolderId()));
		return folderRepository.save(folder);
	}

	private void checkIfCreateNewRootFolder(Folder folder) throws CannotCreateRootException {
		if (isNull(folder.getFatherFolderId())) {
			throw new CannotCreateRootException(CANNOT_CREATE_ROOT_EXCEPTION_MESSAGE);
		}
	}

	private void checkIfMoveRootFolder(Folder folder) throws CannotMoveRootFolderException {
		if (isRoot(folder)) {
			throw new CannotMoveRootFolderException(CANNOT_MOVE_ROOT_FOLDER_EXCEPTION_MESSAGE);
		}
	}

	private void checkIfFolderNotExistsInFatherFolder(Folder folder) throws FolderNotFoundException,
			FolderAlreadyExistsException {
		Folder fatherFolder = getFolderById(folder.getFatherFolderId());
		if (isPresentInFatherFolderFolderWithName(fatherFolder, folder.getFolderName())) {
			throw new FolderAlreadyExistsException(FOLDER_ALREADY_EXISTS_EXCEPTION_MESSAGE + fatherFolder.getFolderName());
		}
	}

	private void checkIfFolderWithIdExists(Long folderId) throws FolderNotFoundException {
		if (folderRepository.findById(folderId).isEmpty()) {
			throw new FolderNotFoundException(FOLDER_NOT_FOUND_EXCEPTION_MESSAGE);
		}
	}

	@Override
	public void delete(Long folderId) throws FolderNotFoundException, CannotDeleteRootFolderException {
		Folder foundFolder = getFolderById(folderId);
		if (isRoot(foundFolder)) {
			throw new CannotDeleteRootFolderException(CANNOT_DELETE_ROOT_FOLDER_EXCEPTION_MESSAGE);
		}
		folderRepository.delete(foundFolder);
	}

	@Override
	public boolean isRoot(Folder folder) {
		return folderRepository.findFolderByFatherFolderIsNull().getFolderId().equals(folder.getFolderId());
	}

	@Override
	public FolderPathDto getFullFolderPath(Folder folder) {
		String folderName = folder.getFolderName();
		String path = (isRoot(folder))
				? folderName
				: (folderRepository.getFullFolderPath(folderName));
		return new FolderPathDto(path);
	}

	@Override
	public boolean isPresentInFatherFolderFolderWithName(Folder fatherFolder, String folderName) {
		return folderRepository.getFolderByFatherFolderAndFolderName(fatherFolder, folderName).isPresent();
	}
}
