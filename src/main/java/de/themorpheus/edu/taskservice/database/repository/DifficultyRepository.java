package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.DifficultyModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DifficultyRepository extends JpaRepository<DifficultyModel, Integer> {

	Optional<DifficultyModel> findByNameKeyIgnoreCase(String nameKey);

	boolean existsByNameKeyIgnoreCase(String nameKey);

}
