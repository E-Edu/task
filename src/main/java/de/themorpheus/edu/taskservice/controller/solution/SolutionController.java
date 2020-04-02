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

	public ControllerResult<SolutionModel> createSolution(SolutionModel solutionModel) {
		if (this.solutionRepository.findById(solutionModel.getSolutionId()).isPresent())
			return ControllerResult.of(Error.ALREADY_EXISTS, "solution");

		return ControllerResult.of(this.solutionRepository.save(solutionModel));
	}

	public ControllerResult<SolutionModel> getSolution(int taskId, String nameKey) {
		TaskModel taskModel = new TaskModel();
		taskModel.setTaskId(taskId);
		return this.getSolution(taskModel, nameKey);
	}

	private ControllerResult<SolutionModel> getSolution(TaskModel taskModel, String nameKey) {
		SolutionModel solutionModel = this.solutionRepository.findSolutionModelByTaskId(taskModel);
		if (solutionModel == null) return ControllerResult.of(Error.NOT_FOUND, "solution");
		if (!solutionModel.getSolutionType().equals(nameKey)) return ControllerResult.of(Error.WRONG_TYPE, "solution");

		return ControllerResult.of(solutionModel);
	}

	public ControllerResult<SolutionModel> deleteSolution(SolutionModel solutionModel) {
		this.solutionRepository.delete(solutionModel);
		return ControllerResult.empty();
	}

	protected ControllerResult<SolutionModel> getSolutionAndCreateIfNotExists(int taskId, String nameKey) {
		ControllerResult<TaskModel> taskModelControllerResult = this.taskController.getTaskById(taskId);
		if (taskModelControllerResult.isResultNotPresent()) return ControllerResult.ret(taskModelControllerResult);

		SolutionModel solutionModel = this.solutionRepository.findSolutionModelByTaskId(taskModelControllerResult.getResult());
		if (solutionModel == null)
			this.solutionRepository.save(new SolutionModel(-1, taskModelControllerResult.getResult(), nameKey));
		else if (!solutionModel.getSolutionType().equals(nameKey)) return ControllerResult.of(Error.WRONG_TYPE);

		ControllerResult<SolutionModel> solutionModelControllerResult = this.getSolution(taskModelControllerResult.getResult().getTaskId(), nameKey);
		if (solutionModelControllerResult.isResultNotPresent())
			return ControllerResult.ret(solutionModelControllerResult);

		return ControllerResult.of(solutionModelControllerResult.getResult());
	}

}
