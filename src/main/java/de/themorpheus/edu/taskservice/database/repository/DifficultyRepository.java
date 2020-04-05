package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.DifficultyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DifficultyRepository extends JpaRepository<DifficultyModel, Integer> {

	void deleteDifficultyByNameKeyIgnoreCase(String nameKey);

	DifficultyModel getDifficultyByDifficultyId(int difficultyId);

	DifficultyModel getDifficultyByNameKeyIgnoreCase(String nameKey);

	boolean existsByNameKey(String nameKey);

}
