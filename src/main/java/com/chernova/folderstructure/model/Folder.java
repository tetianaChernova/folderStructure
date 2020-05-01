package com.chernova.folderstructure.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "folders")
@JsonIgnoreProperties(value = "fatherFolder")
public class Folder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long folderId;

	@NotNull
	private String folderName;

	@JoinColumn(name = "father_folder_id")
	@ManyToOne(targetEntity = Folder.class)
	@JsonIgnore
	private Folder fatherFolder;

	@Column(name = "father_folder_id", insertable = false, updatable = false)
	private Long fatherFolderId;

}
