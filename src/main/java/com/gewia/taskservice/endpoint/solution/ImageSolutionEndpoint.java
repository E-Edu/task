package com.gewia.taskservice.endpoint.solution;

import com.gewia.taskservice.controller.solution.ImageSolutionController;
import com.gewia.taskservice.endpoint.dto.request.solution.CheckImageSolutionRequestDTO;
import com.gewia.taskservice.endpoint.dto.request.solution.CreateImageSolutionRequestDTO;
import com.gewia.taskservice.endpoint.dto.request.solution.UpdateImageSolutionRequestDTO;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.annotation.Timed;

@Timed
@RestController
public class ImageSolutionEndpoint {

	@Autowired private ImageSolutionController imageSolutionController;

	//TODO: Just teacher access
	@GetMapping("/solution/image/user/{taskId}")
	public Object getAllUserImageSolutions(@PathVariable @Min(1) int taskId) {
		return this.imageSolutionController.getAllUserImageSolution(taskId).getHttpResponse();
	}

	@PostMapping("/solution/image")
	public Object createImageSolution(@RequestBody @Valid CreateImageSolutionRequestDTO dto) {
		return this.imageSolutionController.createImageSolution(dto).getHttpResponse();
	}

	@PostMapping("/solution/image/check")
	public Object checkImageSolution(@RequestBody @Valid CheckImageSolutionRequestDTO dto) {
		return this.imageSolutionController.checkImageSolution(dto).getHttpResponse();
	}

	@PutMapping("/solution/image")
	public Object updateImageSolution(@RequestBody @Valid UpdateImageSolutionRequestDTO dto) {
		return this.imageSolutionController.updateImageSolution(dto).getHttpResponse();
	}

	@DeleteMapping("/solution/image/{taskId}")
	public Object deleteImageSolution(@PathVariable @Min(1) int taskId) {
		return this.imageSolutionController.deleteImageSolution(taskId).getHttpResponse();
	}

}
