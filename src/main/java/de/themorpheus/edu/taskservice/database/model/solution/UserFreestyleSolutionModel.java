package de.themorpheus.edu.taskservice.database.model.solution;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetAllUserFreestyleSolutionsResponseDTOModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class UserFreestyleSolutionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userFreestyleSolutionId;

	@ManyToOne
	private FreestyleSolutionModel freestyleSolutionId;
	private String solution;

	@Column(columnDefinition = "BINARY(16)", nullable = false)
	private UUID userId;

	public GetAllUserFreestyleSolutionsResponseDTOModel toGetAllResponseDTOModel() {
		return new GetAllUserFreestyleSolutionsResponseDTOModel(this.userId, this.solution);
	}

}
