package com.chernova.folderstructure.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long fileId;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "folder_id")
	private Folder folder;

	@NotNull
	private String fileName;

	@NotNull
	private String mimeType;

	@NotNull
	private Long fileSize;

}
