package com.chernova.folderstructure.service;

import com.chernova.folderstructure.model.Folder;

public interface FolderService {

	Folder save(Folder folder);

	Folder getFolderById(Long folderId);

	Folder update(Folder folder);

	void delete(Long folderId);
}
