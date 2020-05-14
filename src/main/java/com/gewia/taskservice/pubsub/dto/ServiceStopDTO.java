package com.gewia.taskservice.pubsub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@PubSubDTO("service.stop")
public class ServiceStopDTO {

	private String service;

}
