package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import javax.annotation.PostConstruct;

interface Solution {

	/**
	 * Executed when a task is deleted.
	 *
	 * <p>This method has to delete all solutions by a given <i>SolutionModel</i></p>
	 *
	 * @param solution the given <i>SolutionModel</i>
	 *
	 * @see SolutionModel
	 */
	void deleteAll(SolutionModel solution);

	/**
	 * This method has to be called every time a <i>GenericSolution</i> is deleted.
	 *
	 * <p>Method has to checks if there is an entry in the table with a given <i>SolutionModel</i> left and
	 * deletes this given <i>SolutionModel</i> in <i>SolutionController</i></p>
	 *
	 * @param solutionId the given <i>SolutionModel</i>
	 *
	 * @see SolutionController
	 * @see SolutionModel
	 */
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
