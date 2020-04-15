package de.themorpheus.edu.taskservice.database.repository.solution;

import de.themorpheus.edu.taskservice.database.model.solution.MultipleChoiceSolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MultipleChoiceSolutionRepository extends JpaRepository<MultipleChoiceSolutionModel, Integer> {

	void deleteMultipleChoiceSolutionBySolutionIdAndSolution(SolutionModel solutionId, String solution);

	void deleteAllMultipleChoiceSolutionsBySolutionId(SolutionModel solutionId);

	boolean existsBySolution(String solution);

	List<MultipleChoiceSolutionModel> findAllMultipleChoiceSolutionsBySolutionIdOrderBySolutionDesc(SolutionModel solutionId);

	boolean existsBySolutionId(SolutionModel solutionId);

}
