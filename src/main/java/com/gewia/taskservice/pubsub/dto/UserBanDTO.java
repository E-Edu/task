package com.gewia.taskservice.pubsub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@PubSubDTO("report.user.ban")
public class UserBanDTO {

	private UUID userId;

}
