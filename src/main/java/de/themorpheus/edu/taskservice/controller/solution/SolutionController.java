package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.controller.TaskController;
import de.themorpheus.edu.taskservice.database.model.TaskModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SolutionRepository;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Controller;

@Controller
public class SolutionController {

	private static List<Object> solutionControllers = new ArrayList<>();

	@Autowired private SolutionRepository solutionRepository;
	@Autowired private TaskController taskController;

	public ControllerResult<SolutionModel> createSolution(SolutionModel solutionModel) {
		if (this.solutionRepository.findById(solutionModel.getSolutionId()).isPresent())
			return ControllerResult.of(Error.ALREADY_EXISTS, "solution");

		return ControllerResult.of(this.solutionRepository.save(solutionModel));
	}

	public ControllerResult<SolutionModel> getSolution(int taskId, String nameKey) {
		return this.getGenericSolution(TaskModel.create(taskId), nameKey);
	}

	public ControllerResult<SolutionModel> getSolution(int taskId) {
		return ControllerResult.of(this.solutionRepository.findSolutionModelByTaskId(TaskModel.create(taskId)));
	}

	private ControllerResult<SolutionModel> getGenericSolution(TaskModel taskModel, String solutionTypeNameKey) {
		SolutionModel solutionModel = this.solutionRepository.findSolutionModelByTaskId(taskModel);
		if (solutionModel == null) return ControllerResult.of(Error.NOT_FOUND, "solution");
		if (!solutionModel.getSolutionType().equals(solutionTypeNameKey))
			return ControllerResult.of(Error.WRONG_TYPE, "solution");

		return ControllerResult.of(solutionModel);
	}

	public ControllerResult<SolutionModel> deleteSolutions(int taskId) {
		ControllerResult<SolutionModel> solutionModel = this.getSolution(taskId);
		if (solutionModel.isResultNotPresent()) return ControllerResult.ret(solutionModel);

		this.solutionRepository.delete(solutionModel.getResult());

		for (Object solutionController : solutionControllers) {
			Solution solution = (Solution) solutionController;
			solution.deleteAll(solutionModel.getResult().getTaskId().getTaskId());
		}

		return ControllerResult.empty();
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

	@Bean
	public void getSolutionInterfaces() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
		provider.addIncludeFilter((TypeFilter) (metadataReader, metadataReaderFactory) -> {
			String[] interfaceNames = metadataReader.getClassMetadata().getInterfaceNames();
			if (interfaceNames.length == 0) return false;
			for (String interfaceName : interfaceNames) if (interfaceName.equals(Solution.class.getName())) return true;

			return false;
		});

		for (BeanDefinition beanDefinition : provider.findCandidateComponents("de.themorpheus")) {
			Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
			solutionControllers.add(clazz.getDeclaredConstructor().newInstance());
		}
	}

}
