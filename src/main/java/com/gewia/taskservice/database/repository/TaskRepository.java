package com.gewia.taskservice.database.repository;

import com.gewia.taskservice.database.model.DifficultyModel;
import com.gewia.taskservice.database.model.LectureModel;
import com.gewia.taskservice.database.model.TaskModel;
import com.gewia.taskservice.database.model.TaskTypeModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Integer> {

	Optional<TaskModel> findByTaskId(int taskId);

	List<TaskModel> findAllByLectureId(LectureModel lectureId, Pageable pageable);

	List<TaskModel> findAllByAuthorId(UUID authorId, Pageable pageable);

	List<TaskModel> findAllByLectureId(LectureModel lectureId);

	List<TaskModel> findAllByAuthorId(UUID authorId);

	boolean existsByLectureId(LectureModel lectureId);

	boolean existsByDifficultyId(DifficultyModel difficultyId);

	boolean existsByTaskTypeId(TaskTypeModel taskTypeId);

}
