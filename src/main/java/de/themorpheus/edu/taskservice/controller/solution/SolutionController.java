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
		return this.getSolution(TaskModel.create(taskId), nameKey);
	}

	private ControllerResult<SolutionModel> getSolution(TaskModel taskModel, String solutionTypeNameKey) {
		SolutionModel solutionModel = this.solutionRepository.findSolutionModelByTaskId(taskModel);
		if (solutionModel == null) return ControllerResult.of(Error.NOT_FOUND, "solution");
		if (!solutionModel.getSolutionType().equals(solutionTypeNameKey))
			return ControllerResult.of(Error.WRONG_TYPE, "solution");

		return ControllerResult.of(solutionModel);
	}

	public ControllerResult<SolutionModel> deleteSolution(SolutionModel solutionModel) {
		this.solutionRepository.delete(solutionModel);

		return ControllerResult.empty();
	}

	/**
	 * Gets the solution if exisiting or creates a new one with given <i>taskId & solutionTypeNameKey</i>.
	 *
	 * @param taskId the taskId that belongs to the solution
	 * @param solutionTypeNameKey the name key of the solution type
	 *
	 * @return the existing solution or a new instance
	 */
	protected ControllerResult<SolutionModel> getOrCreateSolution(int taskId, String solutionTypeNameKey) {
		ControllerResult<TaskModel> taskModelControllerResult = this.taskController.getTaskById(taskId);
		if (taskModelControllerResult.isResultNotPresent()) return ControllerResult.ret(taskModelControllerResult);

		SolutionModel solutionModel = this.solutionRepository.findSolutionModelByTaskId(taskModelControllerResult.getResult());
		if (solutionModel == null)
			this.solutionRepository.save(new SolutionModel(-1, taskModelControllerResult.getResult(), solutionTypeNameKey));
		else if (!solutionModel.getSolutionType().equals(solutionTypeNameKey)) return ControllerResult.of(Error.WRONG_TYPE);

		ControllerResult<SolutionModel> solutionModelControllerResult = this.getSolution(
			taskModelControllerResult.getResult().getTaskId(),
			solutionTypeNameKey
		);
		if (solutionModelControllerResult.isResultNotPresent()) return ControllerResult.ret(solutionModelControllerResult);

		return ControllerResult.of(solutionModelControllerResult.getResult());
	}

}
