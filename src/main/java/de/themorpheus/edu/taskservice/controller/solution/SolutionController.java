package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SolutionRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SolutionController {

	@Autowired SolutionRepository solutionRepository;

	public ControllerResult<SolutionModel> create(SolutionModel solutionModel) {
		return ControllerResult.of(this.solutionRepository.save(solutionModel));
	}

	public ControllerResult<SolutionModel> update(SolutionModel solutionModel) {
		ControllerResult<SolutionModel> solutionModelControllerResult = this.get(solutionModel.getTaskId().getTaskId());
		if (solutionModelControllerResult.isErrorPresent()) return solutionModelControllerResult;
		if (solutionModelControllerResult.isResultNotPresent()) return ControllerResult.of(Error.NOT_FOUND);

		return ControllerResult.of(this.solutionRepository.save(solutionModel));
	}

	public ControllerResult<SolutionModel> get(int taskId) {
		Optional<SolutionModel> solutionModel = Optional.ofNullable(this.solutionRepository.findSolutionModelByTaskId(taskId));
		return solutionModel.map(ControllerResult::of).orElseGet(ControllerResult::empty);
	}

	public ControllerResult<SolutionModel> delete(SolutionModel solutionModel) {
		this.solutionRepository.delete(solutionModel);
		return ControllerResult.empty();
	}

}
