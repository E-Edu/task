package de.themorpheus.edu.taskservice.endpoint.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteTaskRequestDTO {

	private int vote;

}
