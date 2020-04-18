package de.themorpheus.edu.taskservice.endpoint.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarkTaskAsDoneDTO {

	@Min(1)
	private int taskId;

	private Date date;
}
