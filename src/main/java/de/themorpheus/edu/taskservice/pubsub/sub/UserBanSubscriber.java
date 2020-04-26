package de.themorpheus.edu.taskservice.pubsub.sub;

import de.themorpheus.edu.taskservice.controller.user.UserDataHandler;
import de.themorpheus.edu.taskservice.database.model.UserBanModel;
import de.themorpheus.edu.taskservice.database.repository.UserBanRepository;
import de.themorpheus.edu.taskservice.pubsub.dto.UserBanDTO;
import de.themorpheus.edu.taskservice.pubsub.sub.api.AbstractObjectSubscriber;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBanSubscriber extends AbstractObjectSubscriber<UserBanDTO> implements UserDataHandler {

	@Autowired private UserBanRepository userBanRepository;

	public UserBanSubscriber() {
		super(UserBanDTO.class);
	}

	@Override
	public void receive(UserBanDTO msg) {
		this.userBanRepository.save(new UserBanModel(-1, msg.getUserId()));
	}


	@Override
	public void deleteOrMaskUserData(UUID userId) {
		this.userBanRepository.deleteAllByUserId(userId);
	}

	@Override
	public ControllerResult<Object> getUserData(UUID userId) {
		List<UserBanModel> userBans = this.userBanRepository.findAllByUserId(userId);
		if (userBans.isEmpty()) return ControllerResult.of(Error.NOT_FOUND);

		return ControllerResult.of(userBans);
	}

}
