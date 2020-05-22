package com.gewia.taskservice.database.model;

import com.gewia.taskservice.endpoint.dto.response.GetModuleResponseDTO;
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
public class ModuleModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int moduleId;

	@NotBlank
	@Column(unique = true)
	private String nameKey;
	@ManyToOne
	private SubjectModel subjectId;

	public GetModuleResponseDTO toResponseDTO() {
		return new GetModuleResponseDTO(this.moduleId, this.nameKey, this.subjectId.getSubjectId());
	}

}
