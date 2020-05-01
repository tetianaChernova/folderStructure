package com.chernova.folderstructure.service.impl;

import com.chernova.folderstructure.exception.CannotCreateFileInNonExistingFolderException;
import com.chernova.folderstructure.exception.FileAlreadyExistsInFolderException;
import com.chernova.folderstructure.exception.FileNotFoundException;
import com.chernova.folderstructure.exception.FolderNotFoundException;
import com.chernova.folderstructure.model.File;
import com.chernova.folderstructure.model.Folder;
import com.chernova.folderstructure.repo.FileRepository;
import com.chernova.folderstructure.service.FolderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DefaultFileServiceTest {

	private static final Long FILE_ID = 1L;
	private static final Long FOLDER_ID = 202L;
	private static final String FILE_NAME = "test.jpg";
	private static final String FOLDER_NAME = "Folder_1";
	private static final String FILE_NOT_FOUND_EXCEPTION_MESSAGE = "File not found with id: ";
	private static final String FILE_ALREADY_EXISTS_EXCEPTION_MESSAGE =
			"File with such name already exists in directory ";
	private static final String CANNOT_CREATE_FILE_IN_NON_EXISTING_FOLDER_EXCEPTION_MESSAGE =
			"Cannot create file in non existing folder";

	@InjectMocks
	private DefaultFileService testInstance;

	@Mock
	private FileRepository fileRepository;
	@Mock
	private FolderService folderService;

	private File file;
	private Folder folder;

	@BeforeEach
	void setUp() {
		file = new File();
		folder = new Folder();
		folder.setFolderId(FOLDER_ID);
		folder.setFolderName(FOLDER_NAME);
		file.setFileId(FILE_ID);
		file.setFolderId(FOLDER_ID);
		file.setFileName(FILE_NAME);
	}

	@Test
	public void shouldReturnFileById() throws FileNotFoundException {
		when(fileRepository.findById(FILE_ID)).thenReturn(ofNullable(file));

		File actualFile = testInstance.getFileById(FILE_ID);

		assertEquals(file, actualFile);
	}

	@Test
	public void shouldThrowFileNotFoundExceptionWhenFileByIdNotExists() {
		when(fileRepository.findById(FILE_ID)).thenReturn(Optional.empty());

		FileNotFoundException fileNotFoundException = assertThrows(FileNotFoundException.class,
				() -> testInstance.getFileById(FILE_ID));

		assertTrue(fileNotFoundException.getMessage().contains(FILE_NOT_FOUND_EXCEPTION_MESSAGE + FILE_ID));
	}

	@Test
	public void shouldNotSaveFileInNonExistingFolder() {
		file.setFolderId(null);

		CannotCreateFileInNonExistingFolderException cannotCreateFileInNonExistingFolderException =
				assertThrows(CannotCreateFileInNonExistingFolderException.class,
						() -> testInstance.save(file));

		assertTrue(cannotCreateFileInNonExistingFolderException.getMessage()
				.contains(CANNOT_CREATE_FILE_IN_NON_EXISTING_FOLDER_EXCEPTION_MESSAGE));
		verify(fileRepository, never()).save(file);
	}

	@Test
	public void shouldNotSaveFileWhenFolderNotFound() throws FolderNotFoundException {
		when(folderService.getFolderById(FOLDER_ID)).thenThrow(FolderNotFoundException.class);

		assertThrows(FolderNotFoundException.class, () -> testInstance.save(file));

		verify(fileRepository, never()).save(file);
	}

	@Test
	public void shouldNotSaveWhenFolderContainsFileWithSameFileName() throws FolderNotFoundException {
		when(folderService.getFolderById(FOLDER_ID)).thenReturn(folder);
		when(fileRepository.getFileByFolderAndFileName(folder, FILE_NAME)).thenReturn(Optional.ofNullable(file));

		FileAlreadyExistsInFolderException fileAlreadyExistsInFolderException =
				assertThrows(FileAlreadyExistsInFolderException.class,
						() -> testInstance.save(file));

		assertTrue(fileAlreadyExistsInFolderException.getMessage()
				.contains(FILE_ALREADY_EXISTS_EXCEPTION_MESSAGE + FOLDER_NAME));
		verify(fileRepository, never()).save(file);
	}

	@Test
	public void shouldSaveFile() throws FolderNotFoundException, FileAlreadyExistsInFolderException,
			CannotCreateFileInNonExistingFolderException {
		when(folderService.getFolderById(FOLDER_ID)).thenReturn(folder);
		when(fileRepository.getFileByFolderAndFileName(folder, FILE_NAME)).thenReturn(Optional.empty());
		when(fileRepository.save(file)).thenReturn(file);

		File actualFile = testInstance.save(file);

		assertEquals(file, actualFile);
		verify(fileRepository).save(file);
	}

}