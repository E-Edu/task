package de.themorpheus.edu.taskservice.pubsub.sub;

import de.themorpheus.edu.taskservice.database.model.UserBanModel;
import de.themorpheus.edu.taskservice.database.repository.UserBanRepository;
import de.themorpheus.edu.taskservice.pubsub.dto.UserBanDTO;
import de.themorpheus.edu.taskservice.pubsub.sub.api.AbstractObjectSubscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBanSubscriber extends AbstractObjectSubscriber<UserBanDTO> {

	@Autowired private UserBanRepository userBanRepository;

	public UserBanSubscriber() {
		super(UserBanDTO.class);
	}

	@Override
	public void receive(UserBanDTO msg) {
		userBanRepository.save(new UserBanModel(-1, msg.getUserId()));
	}



}
