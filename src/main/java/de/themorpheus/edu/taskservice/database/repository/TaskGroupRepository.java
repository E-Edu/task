package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.LectureModel;
import de.themorpheus.edu.taskservice.database.model.TaskGroupModel;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskGroupRepository extends JpaRepository<TaskGroupModel, Integer> {

	List<TaskGroupModel> findAllByLectureId(LectureModel lectureId);

	List<TaskGroupModel> findAllByAuthorId(UUID userId);

	List<TaskGroupModel> findAllByLectureId(LectureModel lectureId, Pageable pageable);

	List<TaskGroupModel> findAllByAuthorId(UUID userId, Pageable pageable);

}
