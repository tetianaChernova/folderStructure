package com.chernova.folderstructure.controller;

import com.chernova.folderstructure.dto.FolderPathDto;
import com.chernova.folderstructure.exception.CannotCreateRootException;
import com.chernova.folderstructure.exception.CannotDeleteRootFolderException;
import com.chernova.folderstructure.exception.CannotMoveRootFolderException;
import com.chernova.folderstructure.exception.FolderAlreadyExistsException;
import com.chernova.folderstructure.exception.FolderNotFoundException;
import com.chernova.folderstructure.model.Folder;
import com.chernova.folderstructure.service.FolderService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/folder")
public class FolderController {

	@Resource
	private FolderService folderService;

	@PostMapping
	public ResponseEntity<?> saveFolder(@RequestBody Folder folder) throws FolderAlreadyExistsException,
			CannotCreateRootException, FolderNotFoundException {
		Folder createdFolder = folderService.save(folder);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{folderId}")
				.buildAndExpand(createdFolder.getFolderId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{folderId}")
	public ResponseEntity<?> getFolderByFolderId(@PathVariable Long folderId) throws FolderNotFoundException {
		Folder foundFolder = folderService.getFolderById(folderId);
		return ResponseEntity.ok(foundFolder);
	}

	@PutMapping("/{folderId}")
	public ResponseEntity<?> updateFolder(@PathVariable Long folderId, @RequestBody Folder folder)
			throws FolderNotFoundException, FolderAlreadyExistsException, CannotMoveRootFolderException,
			CannotCreateRootException {
		Folder updatedFolder = folderService.update(folder, folderId);
		return ResponseEntity.ok(updatedFolder);
	}

	@DeleteMapping("/{folderId}")
	public ResponseEntity<?> deleteFolder(@PathVariable Long folderId) throws FolderNotFoundException,
			CannotDeleteRootFolderException {
		folderService.delete(folderId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{folderId}/path")
	public ResponseEntity<?> getFullFolderPath(@PathVariable Long folderId) throws FolderNotFoundException {
		Folder foundFolder = folderService.getFolderById(folderId);
		FolderPathDto folderPathDto = folderService.getFullFolderPath(foundFolder);
		return ResponseEntity.ok(folderPathDto);
	}

}
