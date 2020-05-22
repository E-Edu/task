package com.gewia.taskservice.database.repository.solution;

import com.gewia.taskservice.database.model.solution.SolutionModel;
import com.gewia.taskservice.database.model.solution.FreestyleSolutionModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FreestyleSolutionRepository extends JpaRepository<FreestyleSolutionModel, Integer> {

	boolean existsBySolutionId(SolutionModel solutionId);

	Optional<FreestyleSolutionModel> findBySolutionId(SolutionModel solutionId);

	void deleteBySolutionId(SolutionModel solutionId);

}
