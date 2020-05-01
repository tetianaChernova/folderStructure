package com.chernova.folderstructure.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "files")
public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fileId;

	@ManyToOne(targetEntity = Folder.class)
	@JoinColumn(name = "folder_id")
	@JsonIgnore
	private Folder folder;

	@NotNull
	@Column(name = "folder_id", insertable = false, updatable = false)
	private Long folderId;

	@NotNull
	private String fileName;

	@NotNull
	private String mimeType;

	@NotNull
	private Long fileSize;

}
