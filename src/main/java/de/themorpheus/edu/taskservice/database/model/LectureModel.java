package de.themorpheus.edu.taskservice.database.model;

import de.themorpheus.edu.taskservice.endpoint.dto.response.GetLectureResponseDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class LectureModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int lectureId;

	@NotBlank
	@Column(unique = true)
	private String nameKey;
	@ManyToOne
	private ModuleModel moduleId;

	public GetLectureResponseDTO toResponseDTO() {
		return new GetLectureResponseDTO(this.lectureId, this.nameKey, this.moduleId.getModuleId());
	}

}
