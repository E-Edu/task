package com.gewia.taskservice.database.repository.solution;

import com.gewia.taskservice.database.model.solution.SolutionModel;
import com.gewia.taskservice.database.model.solution.AnagramSolutionModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnagramSolutionRepository extends JpaRepository<AnagramSolutionModel, Integer> {

	boolean existsBySolutionId(SolutionModel solutionId);

	Optional<AnagramSolutionModel> findBySolutionId(SolutionModel solutionId);

	void deleteBySolutionId(SolutionModel solutionId);

}
