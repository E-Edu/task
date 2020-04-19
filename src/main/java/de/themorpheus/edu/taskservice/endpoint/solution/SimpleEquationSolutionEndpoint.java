package de.themorpheus.edu.taskservice.endpoint.solution;

import de.themorpheus.edu.taskservice.controller.solution.SimpleEquationSolutionController;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckSimpleEquationSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.UpdateSimpleEquationSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CreateSimpleEquationSolutionRequestDTO;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.annotation.Timed;

@Timed
@RestController
public class SimpleEquationSolutionEndpoint {

	@Autowired private SimpleEquationSolutionController simpleEquationSolutionController;

	@PostMapping("/solution/simple_equation")
	public Object createSimpleEquationSolution(@RequestBody @Valid CreateSimpleEquationSolutionRequestDTO dto) {
		return this.simpleEquationSolutionController.createSimpleEquationSolution(dto).getHttpResponse();
	}

	@PutMapping("/solution/simple_equation")
	public Object updateSimpleEquationSolution(@RequestBody @Valid UpdateSimpleEquationSolutionRequestDTO dto) {
		return this.simpleEquationSolutionController.updateSimpleEquationSolution(dto).getHttpResponse();
	}

	@PostMapping("/solution/simple_equation/check")
	public Object checkSimpleEquationSolution(@RequestBody @Valid CheckSimpleEquationSolutionRequestDTO dto) {
		return this.simpleEquationSolutionController.checkSimpleEquationSolution(dto).getHttpResponse();
	}

	@DeleteMapping("/solution/simple_equation/{taskId}")
	public Object deleteSimpleEquationSolution(@PathVariable @Min(1) int taskId) {
		return this.simpleEquationSolutionController.deleteSimpleEquationSolution(taskId).getHttpResponse();
	}

}
