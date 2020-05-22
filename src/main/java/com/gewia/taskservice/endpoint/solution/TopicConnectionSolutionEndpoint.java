package com.gewia.taskservice.endpoint.solution;

import com.gewia.taskservice.controller.solution.TopicConnectionSolutionController;
import com.gewia.taskservice.endpoint.dto.request.solution.CheckTopicConnectionSolutionRequestDTO;
import com.gewia.taskservice.endpoint.dto.request.solution.CreateTopicConnectionSolutionRequestDTO;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.annotation.Timed;

@Timed
@RestController
public class TopicConnectionSolutionEndpoint {

	@Autowired private TopicConnectionSolutionController topicConnectionSolutionController;

	@PostMapping("/solution/topic_connection")
	public Object createTopicConnectionSolution(@RequestBody @Valid CreateTopicConnectionSolutionRequestDTO dto) {
		return this.topicConnectionSolutionController.createTopicConnectionSolution(dto).getHttpResponse();
	}

	@PostMapping("/solution/topic_connection/check")
	public Object checkTopicConnectionSolution(@RequestBody @Valid CheckTopicConnectionSolutionRequestDTO dto) {
		return this.topicConnectionSolutionController.checkTopicConnectionSolution(dto).getHttpResponse();
	}

	@GetMapping("/solution/topic_connection/{taskId}")
	public Object getTopicConnectionSolution(@PathVariable @Min(1) int taskId) {
		return this.topicConnectionSolutionController.getTopicConnectionSolution(taskId).getHttpResponse();
	}

	@GetMapping("/solution/topic_connection/{taskId}/all") //TODO: Just teacher have permission
	public Object getAllTopicConnectionSolutions(@PathVariable @Min(1) int taskId) {
		return this.topicConnectionSolutionController.getAllTopicConnectionSolutions(taskId).getHttpResponse();
	}

	@DeleteMapping("/solution/topic_connection/{topicConnectionSolutionId}")
	public Object deleteTopicConnectionSolution(@PathVariable @Min(1) int topicConnectionSolutionId) {
		return this.topicConnectionSolutionController.deleteTopicConnectionSolution(topicConnectionSolutionId).getHttpResponse();
	}

}
