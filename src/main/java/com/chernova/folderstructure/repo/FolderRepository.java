package com.chernova.folderstructure.repo;

import com.chernova.folderstructure.model.Folder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import static com.chernova.folderstructure.constants.ConstantQueries.GET_FULL_PATH_QUERY;

public interface FolderRepository extends CrudRepository<Folder, Long> {

	Folder findFolderByFatherFolderIsNull();

	Optional<Folder> getFolderByFatherFolderAndFolderName(Folder fatherFolder, String folderName);

	@Query(nativeQuery = true, value = GET_FULL_PATH_QUERY)
	String getFullFolderPath(@Param("folderName") String folderName);

}
