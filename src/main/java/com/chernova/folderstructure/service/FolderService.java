package com.chernova.folderstructure.service;

import com.chernova.folderstructure.dto.FolderPathDto;
import com.chernova.folderstructure.exception.CannotCreateRootException;
import com.chernova.folderstructure.exception.CannotDeleteRootFolderException;
import com.chernova.folderstructure.exception.CannotMoveRootFolderException;
import com.chernova.folderstructure.exception.FolderAlreadyExistsException;
import com.chernova.folderstructure.exception.FolderNotFoundException;
import com.chernova.folderstructure.model.Folder;

public interface FolderService {

	Folder save(Folder folder) throws FolderAlreadyExistsException, CannotCreateRootException, FolderNotFoundException;

	Folder getFolderById(Long folderId) throws FolderNotFoundException;

	Folder update(Folder folder, Long folderId) throws FolderAlreadyExistsException, FolderNotFoundException,
			CannotMoveRootFolderException, CannotCreateRootException;

	void delete(Long folderId) throws FolderNotFoundException, CannotDeleteRootFolderException;

	boolean isRoot(Folder folder);

	FolderPathDto getFullFolderPath(Folder folder);

	boolean isPresentInFatherFolderFolderWithName(Folder fatherFolder, String folderName);

}
