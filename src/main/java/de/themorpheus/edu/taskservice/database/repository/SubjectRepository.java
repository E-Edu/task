package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectModel, Integer> {

	SubjectModel getSubjectBySubjectId(int subjectId);

	SubjectModel getSubjectByDisplayNameIgnoreCase(String displayName);

}
