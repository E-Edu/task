package com.gewia.taskservice.database.repository.solution;

import com.gewia.taskservice.database.model.solution.SimpleEquationSolutionModel;
import com.gewia.taskservice.database.model.solution.SolutionModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleEquationSolutionRepository extends JpaRepository<SimpleEquationSolutionModel, Integer> {

	void deleteAllBySolutionId(SolutionModel solutionId);

	boolean existsBySolutionId(SolutionModel solutionId);

	List<SimpleEquationSolutionModel> findAllBySolutionId(SolutionModel solutionId);

}
