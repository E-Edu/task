package de.themorpheus.edu.taskservice.database.repository.solution;

import de.themorpheus.edu.taskservice.database.model.solution.WordsaladSolutionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolutionWordsaladRepository extends JpaRepository<WordsaladSolutionModel, Integer> {

}
