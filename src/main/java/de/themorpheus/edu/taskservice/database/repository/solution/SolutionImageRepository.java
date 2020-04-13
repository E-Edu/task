package de.themorpheus.edu.taskservice.database.repository.solution;

import de.themorpheus.edu.taskservice.database.model.solution.ImageSolutionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionImageRepository extends JpaRepository<ImageSolutionModel, Integer> {

}
