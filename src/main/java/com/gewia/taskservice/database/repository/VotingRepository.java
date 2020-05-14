package com.gewia.taskservice.database.repository;

import com.gewia.taskservice.database.model.VotingModel;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingRepository extends JpaRepository<VotingModel, Integer> {

	List<VotingModel> findAllByUserId(UUID userId);

}
