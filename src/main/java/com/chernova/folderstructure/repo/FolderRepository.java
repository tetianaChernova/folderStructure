package com.chernova.folderstructure.repo;

import com.chernova.folderstructure.model.Folder;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FolderRepository extends CrudRepository<Folder, Long> {

	Optional<Folder> getFolderByFolderId(Long folderId);

	Folder findFolderByFatherFolderIdIsNull();
}
