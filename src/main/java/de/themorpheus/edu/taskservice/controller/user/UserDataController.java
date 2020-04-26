package de.themorpheus.edu.taskservice.controller.user;

import de.themorpheus.edu.taskservice.endpoint.dto.response.GetUserDataResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.GetUserDataResponseDTOModel;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UserDataController {

	private static final List<UserDataHandler> USER_HANDLERS = new ArrayList<>();
	private static final String[] PREFIXES_AND_SUFFIXES = new String[]{"Controller", "Endpoint", "DTO", "Model", "Response", "Request"};

	public ControllerResult<GetUserDataResponseDTO> getUserData(UUID userId) {
		List<GetUserDataResponseDTOModel> responseDTOs = new ArrayList<>();
		for (UserDataHandler userHandler : USER_HANDLERS) {
			ControllerResult<Object> result = userHandler.getUserData(userId);
			if (result.isResultPresent() && result.getResult() != null)
				responseDTOs.add(new GetUserDataResponseDTOModel(this.getNameKey(userHandler), result.getHttpResponse()));
		}

		return ControllerResult.of(new GetUserDataResponseDTO(userId, responseDTOs));
	}

	public ControllerResult deleteUserData(UUID userId) {
		USER_HANDLERS.forEach(userHandler -> userHandler.deleteOrMaskUserData(userId));

		return ControllerResult.empty();
	}

	public static void registerUserHandler(UserDataHandler userHandler) {
		USER_HANDLERS.add(userHandler);
	}

	private String getNameKey(UserDataHandler userHandler) {
		String nameKey = userHandler.getClass().getSimpleName();
		for (String prefixOrSuffix : PREFIXES_AND_SUFFIXES) nameKey = nameKey.replaceAll(prefixOrSuffix, "");

		return nameKey;
	}

}
