package com.chernova.folderstructure.controller;

import com.chernova.folderstructure.dto.FilePathDto;
import com.chernova.folderstructure.exception.CannotCreateFileInNonExistingFolderException;
import com.chernova.folderstructure.exception.CannotMoveFileException;
import com.chernova.folderstructure.exception.FileAlreadyExistsInFolderException;
import com.chernova.folderstructure.exception.FileNotFoundException;
import com.chernova.folderstructure.exception.FolderNotFoundException;
import com.chernova.folderstructure.model.File;
import com.chernova.folderstructure.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

	@Resource
	private FileService fileService;

	@PostMapping
	public ResponseEntity<?> saveFile(@RequestBody File file) throws FileAlreadyExistsInFolderException,
			FolderNotFoundException, CannotCreateFileInNonExistingFolderException {
		File createdFile = fileService.save(file);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{fileId}")
				.buildAndExpand(createdFile.getFileId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping(value = "/{fileId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getFileByFileId(@PathVariable Long fileId) throws FileNotFoundException {
		File foundFile = fileService.getFileById(fileId);
		return ResponseEntity.ok(foundFile);
	}

	@PutMapping("/{fileId}")
	public ResponseEntity<?> updateFile(@PathVariable Long fileId, @RequestBody File file)
			throws FolderNotFoundException, FileAlreadyExistsInFolderException, FileNotFoundException,
			CannotMoveFileException {
		File updatedFile = fileService.update(file, fileId);
		return ResponseEntity.ok(updatedFile);
	}

	@DeleteMapping("/{fileId}")
	public ResponseEntity<?> deleteFile(@PathVariable Long fileId) throws FileNotFoundException {
		fileService.delete(fileId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{fileId}/path")
	public ResponseEntity<?> getFullFilePath(@PathVariable Long fileId) throws FileNotFoundException {
		File foundFile = fileService.getFileById(fileId);
		FilePathDto filePathDto = fileService.getFullFilePath(foundFile);
		return ResponseEntity.ok(filePathDto);
	}

}
