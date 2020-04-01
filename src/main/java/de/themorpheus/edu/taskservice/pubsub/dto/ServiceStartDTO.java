package de.themorpheus.edu.taskservice.pubsub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PubSubDTO("service.start")
public class ServiceStartDTO {

	private String service;

}
