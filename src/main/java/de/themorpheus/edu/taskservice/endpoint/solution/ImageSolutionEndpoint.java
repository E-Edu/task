package de.themorpheus.edu.taskservice.endpoint.solution;

import de.themorpheus.edu.taskservice.controller.solution.ImageSolutionController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckImageSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CreateImageSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.UpdateImageSolutionRequestDTO;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Timed
@RestController
public class ImageSolutionEndpoint {

	@Autowired private ImageSolutionController imageSolutionController;

	@PostMapping("/solution/image")
	public Object createImageSolution(@RequestBody @Valid CreateImageSolutionRequestDTO dto) {
		return this.imageSolutionController.createSolutionImage(dto).getHttpResponse();
	}

	@PostMapping("/solution/image/check")
	public Object checkImageSolution(@RequestBody @Valid CheckImageSolutionRequestDTO dto) {
		return this.imageSolutionController.checkSolutionImage(dto).getHttpResponse();
	}

	@GetMapping("/solution/image/{taskId}")
	public Object getImageSolution(@PathVariable @Min(0) int taskId) {
		return this.imageSolutionController.getSolutionImage(taskId).getHttpResponse();
	}

	@PutMapping("/solution/image")
	public Object updateImageSolution(@RequestBody @Valid UpdateImageSolutionRequestDTO dto) {
		return this.imageSolutionController.updateSolutionImage(dto).getHttpResponse();
	}

	@DeleteMapping("/solution/image/{taskId}")
	public Object deleteImageSolution(@PathVariable @Min(0) int taskId) {
		return this.imageSolutionController.deleteSolutionImage(taskId).getHttpResponse();
	}

}
