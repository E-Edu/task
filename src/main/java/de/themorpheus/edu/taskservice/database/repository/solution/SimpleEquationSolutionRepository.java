package de.themorpheus.edu.taskservice.database.repository.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SimpleEquationSolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleEquationSolutionRepository extends JpaRepository<SimpleEquationSolutionModel, Integer> {

	void deleteAllBySolutionId(SolutionModel solutionId);

	boolean existsBySolutionId(SolutionModel solutionId);

	List<SimpleEquationSolutionModel> findAllBySolutionId(SolutionModel solutionId);

}
