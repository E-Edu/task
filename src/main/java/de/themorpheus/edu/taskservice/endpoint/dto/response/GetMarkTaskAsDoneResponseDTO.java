package de.themorpheus.edu.taskservice.endpoint.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMarkTaskAsDoneResponseDTO {

	@Min(1)
	private int taskDoneId;

	@NotNull
	private Date date;

}
