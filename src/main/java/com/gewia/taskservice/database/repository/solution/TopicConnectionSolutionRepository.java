package com.gewia.taskservice.database.repository.solution;

import com.gewia.taskservice.database.model.solution.SolutionModel;
import com.gewia.taskservice.database.model.solution.TopicConnectionSolutionModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicConnectionSolutionRepository extends JpaRepository<TopicConnectionSolutionModel, Integer> {

	boolean existsBySolutionIdAndPointAAndPointB(SolutionModel solutionId, String pointA, String pointB);

	void deleteAllBySolutionId(SolutionModel solutionId);

	boolean existsBySolutionId(SolutionModel solutionId);

	boolean existsBySolutionIdAndPointA(SolutionModel solutionId, String pointA);

	List<TopicConnectionSolutionModel> findAllBySolutionId(SolutionModel solutionId);

}
