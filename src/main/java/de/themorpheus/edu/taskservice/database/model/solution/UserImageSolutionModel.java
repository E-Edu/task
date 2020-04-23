package de.themorpheus.edu.taskservice.database.model.solution;

import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetAllUserImageSolutionsResponseDTOModel;
import java.util.UUID;
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
public class UserImageSolutionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userImageSolutionId;

	@NotBlank
	private String url;
	@ManyToOne
	private ImageSolutionModel imageSolutionId;

	@Column(columnDefinition = "BINARY(16)", nullable = false)
	private UUID userId;

	public GetAllUserImageSolutionsResponseDTOModel toGetAllResponseDTOModel() {
		return new GetAllUserImageSolutionsResponseDTOModel(this.userId, this.url);
	}
}
