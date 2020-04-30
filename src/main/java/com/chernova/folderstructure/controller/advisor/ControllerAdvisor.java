package com.chernova.folderstructure.controller.advisor;

import com.chernova.folderstructure.exception.FileAlreadyExistsInFolderException;
import com.chernova.folderstructure.exception.FileNotFoundException;
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

	private void populateResponseBody(Map<String, Object> body, String message) {
		body.put(TIMESTAMP, LocalDateTime.now());
		body.put(MESSAGE, message);
	}
}
