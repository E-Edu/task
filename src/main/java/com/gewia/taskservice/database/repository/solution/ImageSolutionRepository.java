package com.gewia.taskservice.database.repository.solution;

import com.gewia.taskservice.database.model.solution.ImageSolutionModel;
import com.gewia.taskservice.database.model.solution.SolutionModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageSolutionRepository extends JpaRepository<ImageSolutionModel, Integer> {

	boolean existsBySolutionId(SolutionModel solutionId);

	Optional<ImageSolutionModel> findBySolutionId(SolutionModel solutionId);

	void deleteBySolutionId(SolutionModel solutionId);

}
