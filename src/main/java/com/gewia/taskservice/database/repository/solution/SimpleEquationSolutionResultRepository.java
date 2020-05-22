package com.gewia.taskservice.database.repository.solution;

import com.gewia.taskservice.database.model.solution.SimpleEquationSolutionResultModel;
import com.gewia.taskservice.database.model.solution.SolutionModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleEquationSolutionResultRepository extends JpaRepository<SimpleEquationSolutionResultModel, Integer> {

	void deleteAllBySolutionId(SolutionModel solutionId);

	boolean existsBySolutionId(SolutionModel solutionId);

	Optional<SimpleEquationSolutionResultModel> findBySolutionId(SolutionModel solutionId);

}
