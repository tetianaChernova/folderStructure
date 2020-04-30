package com.chernova.folderstructure.service;

import com.chernova.folderstructure.exception.FolderNotFoundException;
import com.chernova.folderstructure.model.Folder;

public interface FolderService {

	Folder save(Folder folder);

	Folder getFolderById(Long folderId) throws FolderNotFoundException;

	Folder update(Folder folder);

	void delete(Long folderId);

	boolean isRoot(Folder folder);

}
