package com.chernova.folderstructure.service.impl;

import com.chernova.folderstructure.model.Folder;
import com.chernova.folderstructure.repo.FolderRepository;
import com.chernova.folderstructure.service.FolderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DefaultFolderService implements FolderService {

	@Resource
	private FolderRepository folderRepository;

	@Override
	public Folder save(Folder folder) {
		return folderRepository.save(folder);
	}

	@Override
	public Folder getFolderById(Long folderId) {
		return folderRepository.getFolderByFolderId(folderId);
	}

	@Override
	public Folder update(Folder folder) {
		return folderRepository.save(folder);
	}

	@Override
	public void delete(Long folderId) {
		folderRepository.deleteById(folderId);
	}
}
