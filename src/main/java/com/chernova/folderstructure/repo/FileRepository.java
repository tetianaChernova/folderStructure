package com.chernova.folderstructure.repo;

import com.chernova.folderstructure.model.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long> {

	File getFileByFileId(Long fileId);
}
