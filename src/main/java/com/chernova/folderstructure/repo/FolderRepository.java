package com.chernova.folderstructure.repo;

import com.chernova.folderstructure.model.Folder;
import org.springframework.data.repository.CrudRepository;

public interface FolderRepository extends CrudRepository<Folder, Long> {

	Folder getFolderByFolderId(Long folderId);
}
