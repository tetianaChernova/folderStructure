package com.chernova.folderstructure.service.impl;

import com.chernova.folderstructure.exception.FolderNotFoundException;
import com.chernova.folderstructure.model.Folder;
import com.chernova.folderstructure.repo.FolderRepository;
import com.chernova.folderstructure.service.FolderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DefaultFolderService implements FolderService {

	private static final String FOLDER_NOT_FOUND_EXCEPTION = "Folder not found";
	@Resource
	private FolderRepository folderRepository;

	@Override
	public Folder save(Folder folder) {
		return folderRepository.save(folder);
	}

	@Override
	public Folder getFolderById(Long folderId) throws FolderNotFoundException {
		return folderRepository.findById(folderId)
				.orElseThrow(() -> new FolderNotFoundException(FOLDER_NOT_FOUND_EXCEPTION));
	}

	@Override
	public Folder update(Folder folder) {
		return folderRepository.save(folder);
	}

	@Override
	public void delete(Long folderId) {
		folderRepository.deleteById(folderId);
	}

	@Override
	public boolean isRoot(Folder folder) {
		return folderRepository.findFolderByFatherFolderIdIsNull().equals(folder);
	}

}
