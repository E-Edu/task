package de.themorpheus.edu.taskservice.endpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLectureDTO {

	private String displayName;
	private String moduleDisplayName;

}
