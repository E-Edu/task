package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.controller.TaskController;
import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SolutionRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import static de.themorpheus.edu.taskservice.util.Constants.Solution.NAME_KEY;

@Controller
public class SolutionController {

	private static List<Solution> solutionControllers = new ArrayList<>();

	@Autowired private SolutionRepository solutionRepository;
	@Autowired private TaskController taskController;

	public ControllerResult<SolutionModel> getGenericSolution(int taskId, String nameKey) {
		return this.getGenericSolution(TaskModel.create(taskId), nameKey);
	}

	public ControllerResult<SolutionModel> getSolution(int taskId) {
		Optional<SolutionModel> optionalSolution = this.solutionRepository.findSolutionModelByTaskId(TaskModel.create(taskId));

		return optionalSolution.map(ControllerResult::of).orElseGet(() -> ControllerResult.of(Error.NOT_FOUND, NAME_KEY));
	}

	private ControllerResult<SolutionModel> getGenericSolution(TaskModel task, String solutionTypeNameKey) {
		Optional<SolutionModel> optionalSolution = this.solutionRepository.findSolutionModelByTaskId(task);
		if (!optionalSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		if (!optionalSolution.get().getSolutionType().equals(solutionTypeNameKey))
			return ControllerResult.of(Error.WRONG_TYPE, NAME_KEY);

		return ControllerResult.of(optionalSolution.get());
	}

	public ControllerResult<SolutionModel> deleteAllSolutions(int taskId) {
		ControllerResult<SolutionModel> solution = this.getSolution(taskId);
		if (solution.isResultNotPresent()) return ControllerResult.ret(solution);

		for (Solution solutionController : solutionControllers)
				solutionController.deleteAll(solution.getResult().getTaskId().getTaskId());

		this.solutionRepository.delete(solution.getResult());

		return ControllerResult.empty();
	}

	public void deleteSolution(SolutionModel solutionId) {
		this.solutionRepository.delete(solutionId);
	}

	/**
	 * Gets the solution if existing or creates a new one with given <i>taskId & solutionTypeNameKey</i>.
	 *
	 * @param taskId the taskId that belongs to the solution
	 * @param solutionTypeNameKey the name key of the solution type
	 *
	 * @return the existing solution or a new instance
	 */
	protected ControllerResult<SolutionModel> getOrCreateSolution(int taskId, String solutionTypeNameKey) {
		ControllerResult<TaskModel> taskModelControllerResult = this.taskController.getTaskByTaskId(taskId);
		if (taskModelControllerResult.isResultNotPresent()) return ControllerResult.ret(taskModelControllerResult);

		Optional<SolutionModel> optionalSolution = this.solutionRepository.findSolutionModelByTaskId(taskModelControllerResult.getResult());
		if (!optionalSolution.isPresent())
			this.solutionRepository.save(new SolutionModel(-1, taskModelControllerResult.getResult(), solutionTypeNameKey));
		else if (!optionalSolution.get().getSolutionType().equals(solutionTypeNameKey))
			return ControllerResult.of(Error.WRONG_TYPE, NAME_KEY);

		ControllerResult<SolutionModel> solutionModelControllerResult = this.getGenericSolution(
			taskModelControllerResult.getResult().getTaskId(),
			solutionTypeNameKey
		);
		if (solutionModelControllerResult.isResultNotPresent()) return ControllerResult.ret(solutionModelControllerResult);

		return ControllerResult.of(solutionModelControllerResult.getResult());
	}

	static void registerGenericSolutionController(Solution solution) {
		solutionControllers.add(solution);
	}

}
