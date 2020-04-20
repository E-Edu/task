package de.themorpheus.edu.taskservice.database.repository.solution;

import de.themorpheus.edu.taskservice.database.model.solution.UserFreestyleSolutionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFreestyleSolutionRepository extends JpaRepository<UserFreestyleSolutionModel, Integer> {

}
