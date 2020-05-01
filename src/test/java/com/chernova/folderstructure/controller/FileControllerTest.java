package com.chernova.folderstructure.controller;

import com.chernova.folderstructure.exception.CannotCreateFileInNonExistingFolderException;
import com.chernova.folderstructure.model.File;
import com.chernova.folderstructure.service.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(
		controllers = FileController.class,
		excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class FileControllerTest {

	private static final Long FILE_ID = 1L;
	private static final Long FOLDER_ID = 202L;
	private static final String API_URL = "/file";
	private static final char SLASH = '/';

	@Resource
	private MockMvc mockMvc;

	@MockBean
	private FileService fileService;

	private File file;

	@BeforeEach
	void setUp() {
		file = new File();
		file.setFileId(FILE_ID);
		file.setFolderId(FOLDER_ID);
	}

	@Test
	public void shouldReturnFileById() throws Exception {
		when(fileService.getFileById(FILE_ID)).thenReturn(file);

		ResultActions resultActions = mockMvc.perform(get(API_URL + SLASH + FILE_ID)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		String actualResponseString = resultActions.andReturn().getResponse().getContentAsString();
		String expectedResponseString = asJsonString(file);

		assertEquals(expectedResponseString, actualResponseString);
	}

	@Test
	public void shouldSaveFile() throws Exception {
		when(fileService.save(file)).thenReturn(file);

		mockMvc.perform(post(API_URL)
				.content(asJsonString(file))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	public void shouldReturnConflictWhenCreateFileInNonExistingFolder() throws Exception {
		when(fileService.save(file)).thenThrow(CannotCreateFileInNonExistingFolderException.class);

		mockMvc.perform(post(API_URL)
				.content(asJsonString(file))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict());
	}

	private String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}