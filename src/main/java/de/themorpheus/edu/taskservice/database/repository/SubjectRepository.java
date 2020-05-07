package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.SubjectModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectModel, Integer> {

	Optional<SubjectModel> findByNameKeyIgnoreCase(String nameKey);

	boolean existsByNameKeyIgnoreCase(String nameKey);

}
