package com.chernova.folderstructure.repo;

import com.chernova.folderstructure.model.File;
import com.chernova.folderstructure.model.Folder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FileRepository extends CrudRepository<File, Long> {

	Optional<File> getFileByFolderAndFileName(Folder folder, String fileName);

	@Query(nativeQuery = true, value = """
			WITH RECURSIVE full_path(fold_name, father_fold_name, path) AS (
			    SELECT f1.folder_name, f2.folder_name, CONCAT(f2.folder_name, '/', f1.folder_name)
			    FROM folder2folders f1 INNER JOIN folder2folders f2 ON f1.father_folder_id = f2.folder_id
			    WHERE f1.father_folder_id IS NOT NULL
			    UNION
			    SELECT ff1.folder_name, full_path.father_fold_name, CONCAT(full_path.path, '/', ff1.folder_name)
			    FROM (folder2folders ff1 INNER JOIN folder2folders ff2 ON ff1.father_folder_id = ff2.folder_id) 
			    INNER JOIN full_path ON ff2.folder_name = full_path.fold_name
			)
			SELECT path
			FROM full_path
			WHERE father_fold_name IN (SELECT f.folder_name
			                           FROM folder2folders f
			                           WHERE f.father_folder_id IS NULL
			                           )
			AND fold_name = :folderName
			""")
	String getFullFilePath(@Param("folderName") String folderName);

}
