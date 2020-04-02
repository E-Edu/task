package de.themorpheus.edu.taskservice.database.repository.solution;

import de.themorpheus.edu.taskservice.database.model.solution.MultipleChoiceSolutionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultipleChoiceSolutionRepository extends JpaRepository<MultipleChoiceSolutionModel, Integer> {

	MultipleChoiceSolutionModel getMultipleChoiceSolutionBySolutionAndSolutionId(String solution, int solutionId);

	void deleteMultipleChoiceSolutionBySolutionIdAndSolution(int taskId, String solution);

	void deleteAllMultipleChoiceSolutionsBySolutionId(int solutionId);

	boolean existsBySolution(String solution);

}
