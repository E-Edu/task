package com.gewia.taskservice.database.model.solution;

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
public class MultipleChoiceSolutionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int multipleChoiceSolutionId;

	@ManyToOne
	private SolutionModel solutionId;
	@NotBlank
	private String solution;
	private boolean correct;

}
