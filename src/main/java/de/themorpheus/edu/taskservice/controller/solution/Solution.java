package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;

interface Solution {

	void deleteAll(int taskId);

	void deleteSolutionIdIfDatabaseIsEmpty(SolutionModel solutionId);

}
