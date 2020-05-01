package com.chernova.folderstructure.repo;

import com.chernova.folderstructure.model.File;
import com.chernova.folderstructure.model.Folder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import static com.chernova.folderstructure.constants.ConstantQueries.GET_FULL_PATH_QUERY;

public interface FileRepository extends CrudRepository<File, Long> {

	Optional<File> getFileByFolderAndFileName(Folder folder, String fileName);

	@Query(nativeQuery = true, value = GET_FULL_PATH_QUERY)
	String getFullFilePath(@Param("folderName") String folderName);

}
