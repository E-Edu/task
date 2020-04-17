package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import javax.annotation.PostConstruct;

interface Solution {

	void deleteAll(int taskId);

	void deleteSolutionIdIfDatabaseIsEmpty(SolutionModel solutionId);

	/**
	 * Registers the <i>GenericSolutionController</i> in <i>SolutionController</i>.
	 * <b>Please do not override this method</b>
	 *
	 * @see SolutionController
	 */
	@PostConstruct
	default void registerGenericSolutionController() {
		SolutionController.registerGenericSolutionController(this);
	}

}
