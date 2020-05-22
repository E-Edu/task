package com.gewia.taskservice.pubsub.sub;

import com.gewia.taskservice.controller.user.UserDataHandler;
import com.gewia.taskservice.database.model.UserBanModel;
import com.gewia.taskservice.database.repository.UserBanRepository;
import com.gewia.taskservice.pubsub.dto.UserBanDTO;
import com.gewia.taskservice.pubsub.sub.api.AbstractObjectSubscriber;
import com.gewia.taskservice.util.ControllerResult;
import com.gewia.taskservice.util.Error;
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
