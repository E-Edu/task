package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.controller.TaskController;
import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SolutionRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SolutionController {

	@Autowired private SolutionRepository solutionRepository;
	@Autowired private TaskController taskController;

	public ControllerResult<SolutionModel> create(SolutionModel solutionModel) {
		if (this.solutionRepository.findById(solutionModel.getSolutionId()).isPresent()) return ControllerResult.of(Error.ALREADY_EXISTS, "solution");

		return ControllerResult.of(this.solutionRepository.save(solutionModel));
	}

	public ControllerResult<SolutionModel> get(int taskId) {
		SolutionModel solutionModel = this.solutionRepository.findSolutionModelByTaskId(taskId);
		if (solutionModel == null) return ControllerResult.of(Error.NOT_FOUND, "solution");

		return ControllerResult.of(solutionModel);
	}

	public ControllerResult<SolutionModel> getAndCreateIfNotExists(int taskId, String solutionType) {
		ControllerResult<SolutionModel> solutionModelControllerResult = this.get(taskId);
		if (solutionModelControllerResult.isErrorPresent()) {
			ControllerResult<TaskModel> taskModelControllerResult = this.taskController.getTaskById(taskId);
			if (taskModelControllerResult.isErrorPresent()) return ControllerResult.of(taskModelControllerResult.getError(), taskModelControllerResult.getExtra());

			return ControllerResult.of(this.solutionRepository.save(new SolutionModel(-1, taskModelControllerResult.getResult(), solutionType)));
		} else {
			return solutionModelControllerResult;
		}
	}

	public ControllerResult<SolutionModel> delete(SolutionModel solutionModel) {
		this.solutionRepository.delete(solutionModel);
		return ControllerResult.empty();
	}

	public boolean isSolutionType(int taskId, String nameKey) {
		SolutionModel solutionModel = this.solutionRepository.findSolutionModelByTaskId(taskId);
		if (solutionModel == null) return true;
		return solutionModel.getSolutionType().equals(nameKey);
	}

	public ControllerResult<Integer> getSolutionIdByTaskIdAndNameKey(int taskId, String nameKey) {
		if (!this.isSolutionType(taskId, nameKey)) return ControllerResult.of(Error.WRONG_TYPE, nameKey + "Solution");
		ControllerResult<SolutionModel> controllerResult = this.getAndCreateIfNotExists(taskId, nameKey);
		if (controllerResult.isErrorPresent()) return ControllerResult.of(controllerResult.getError(), controllerResult.getExtra());

		return ControllerResult.of(controllerResult.getResult().getSolutionId());
	}



}
