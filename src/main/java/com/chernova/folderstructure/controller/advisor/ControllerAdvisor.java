package com.chernova.folderstructure.controller.advisor;

import com.chernova.folderstructure.exception.CannotCreateFileInNonExistingFolderException;
import com.chernova.folderstructure.exception.CannotCreateRootException;
import com.chernova.folderstructure.exception.CannotDeleteRootFolderException;
import com.chernova.folderstructure.exception.CannotMoveFileException;
import com.chernova.folderstructure.exception.CannotMoveRootFolderException;
import com.chernova.folderstructure.exception.FileAlreadyExistsInFolderException;
import com.chernova.folderstructure.exception.FileNotFoundException;
import com.chernova.folderstructure.exception.FolderAlreadyExistsException;
import com.chernova.folderstructure.exception.FolderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	public static final String TIMESTAMP = "timestamp";
	public static final String MESSAGE = "message";

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException fileNotFoundException) {
		Map<String, Object> body = new HashMap<>();
		populateResponseBody(body, fileNotFoundException.getMessage());
		log.warn(fileNotFoundException.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FolderNotFoundException.class)
	public ResponseEntity<Object> handleFolderNotFoundException(FolderNotFoundException folderNotFoundException) {
		Map<String, Object> body = new HashMap<>();
		populateResponseBody(body, folderNotFoundException.getMessage());
		log.warn(folderNotFoundException.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FileAlreadyExistsInFolderException.class)
	public ResponseEntity<Object> handleFileAlreadyExistsInFolderException(
			FileAlreadyExistsInFolderException fileAlreadyExistsInFolderException) {
		Map<String, Object> body = new HashMap<>();
		populateResponseBody(body, fileAlreadyExistsInFolderException.getMessage());
		log.warn(fileAlreadyExistsInFolderException.getMessage());
		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(CannotMoveFileException.class)
	public ResponseEntity<Object> handleCannotMoveFileException(
			CannotMoveFileException cannotMoveFileException) {
		Map<String, Object> body = new HashMap<>();
		populateResponseBody(body, cannotMoveFileException.getMessage());
		log.warn(cannotMoveFileException.getMessage());
		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(CannotCreateFileInNonExistingFolderException.class)
	public ResponseEntity<Object> handleCannotCreateFileInNonExistingFolderException(
			CannotCreateFileInNonExistingFolderException cannotCreateFileInNonExistingFolderException) {
		Map<String, Object> body = new HashMap<>();
		populateResponseBody(body, cannotCreateFileInNonExistingFolderException.getMessage());
		log.warn(cannotCreateFileInNonExistingFolderException.getMessage());
		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(FolderAlreadyExistsException.class)
	public ResponseEntity<Object> handleFolderAlreadyExistsException(
			FolderAlreadyExistsException folderAlreadyExistsException) {
		Map<String, Object> body = new HashMap<>();
		populateResponseBody(body, folderAlreadyExistsException.getMessage());
		log.warn(folderAlreadyExistsException.getMessage());
		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(CannotCreateRootException.class)
	public ResponseEntity<Object> handleCannotCreateRootException(
			CannotCreateRootException cannotCreateRootException) {
		Map<String, Object> body = new HashMap<>();
		populateResponseBody(body, cannotCreateRootException.getMessage());
		log.warn(cannotCreateRootException.getMessage());
		return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(CannotMoveRootFolderException.class)
	public ResponseEntity<Object> handleCannotMoveRootFolderException(
			CannotMoveRootFolderException cannotMoveRootFolderException) {
		Map<String, Object> body = new HashMap<>();
		populateResponseBody(body, cannotMoveRootFolderException.getMessage());
		log.warn(cannotMoveRootFolderException.getMessage());
		return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(CannotDeleteRootFolderException.class)
	public ResponseEntity<Object> handleCannotDeleteRootFolderException(
			CannotDeleteRootFolderException cannotDeleteRootFolderException) {
		Map<String, Object> body = new HashMap<>();
		populateResponseBody(body, cannotDeleteRootFolderException.getMessage());
		log.warn(cannotDeleteRootFolderException.getMessage());
		return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
	}

	private void populateResponseBody(Map<String, Object> body, String message) {
		body.put(TIMESTAMP, LocalDateTime.now());
		body.put(MESSAGE, message);
	}
}
