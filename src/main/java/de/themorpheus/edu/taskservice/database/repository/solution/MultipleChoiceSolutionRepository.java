package de.themorpheus.edu.taskservice.database.repository.solution;

import de.themorpheus.edu.taskservice.database.model.solution.MultipleChoiceSolutionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultipleChoiceSolutionRepository extends JpaRepository<MultipleChoiceSolutionModel, Integer> {

	MultipleChoiceSolutionModel findMultipleChoiceSolutionByTaskIdAndSolutionId(int taskId, int solutionId);

	MultipleChoiceSolutionModel getMultipleChoiceSolutionBySolutionAndTaskId(String solution, int taskId);

	void deleteMultipleChoiceSolutionByTaskIdAndSolutionId(int taskId, int solutionId);

	void deleteAllMultipleChoiceSolutionsByTaskId(int taskId);

}
