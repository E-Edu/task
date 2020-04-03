package de.themorpheus.edu.taskservice.pubsub.sub;

import de.themorpheus.edu.taskservice.pubsub.dto.UserBanDTO;
import de.themorpheus.edu.taskservice.pubsub.sub.api.AbstractObjectSubscriber;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class UserBanSubscriber extends AbstractObjectSubscriber<UserBanDTO> {

	@Getter private final Set<UUID> bannedUserIds = new HashSet<>();

	public UserBanSubscriber() {
		super(UserBanDTO.class);
	}

	@Override
	public void receive(UserBanDTO msg) {
		this.getBannedUserIds().add(msg.getUserId());
	}

}
