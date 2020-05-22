package com.gewia.taskservice.database.repository.solution;

import com.gewia.taskservice.database.model.solution.ImageSolutionModel;
import com.gewia.taskservice.database.model.solution.UserImageSolutionModel;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageSolutionRepository extends JpaRepository<UserImageSolutionModel, Integer> {

	List<UserImageSolutionModel> findAllByUserId(UUID userId);

	void deleteAllByUserId(UUID userId);

	List<UserImageSolutionModel> findAllByImageSolutionId(ImageSolutionModel imageSolutionId);

}
