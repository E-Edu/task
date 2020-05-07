package de.themorpheus.edu.taskservice.database.repository;

import de.themorpheus.edu.taskservice.database.model.UserBanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserBanRepository extends JpaRepository<UserBanModel, Integer> {

	boolean existsByUserId(UUID userId);

	void deleteAllByUserId(UUID userId);

	List<UserBanModel> findAllByUserId(UUID userId);

}
