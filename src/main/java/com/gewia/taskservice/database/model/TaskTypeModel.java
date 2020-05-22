package com.gewia.taskservice.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class TaskTypeModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int taskTypeId;

	@NotBlank
	@Column(unique = true)
	private String nameKey;

}
