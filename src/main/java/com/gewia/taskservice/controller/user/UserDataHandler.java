package com.gewia.taskservice.controller.user;

import com.gewia.taskservice.util.ControllerResult;
import java.util.UUID;
import javax.annotation.PostConstruct;

/**
 * This interface has to be implemented by all classes which save userIds.
 */
public interface UserDataHandler {

	/**
	 * Deletes or masks the given userId everywhere.
	 *
	 * <p><b>Important: Just delete the the entry with the userId if there is no foreign key!</b></p>
	 *
	 * @param userId the userId which has to deleted or masked everywhere
	 */
	void deleteOrMaskUserData(UUID userId);

	/**
	 * Collects all data where the userId is used and returns it in json format.
	 *
	 * <p><b>Return an Error if there is nothing found!</b></p>
	 *
	 * @param userId the userId after what the data is collected
	 *
	 * @return a String of collected data in json format
	 */
	ControllerResult<Object> getUserData(UUID userId);

	/**
	 * Registers the class implementing <i>UserHandler</i> in <i>UserController</i>.
	 * <b>Please do not override this method</b>
	 *
	 * @see UserDataController
	 */
	@PostConstruct
	default void register() {
		UserDataController.registerUserHandler(this);
	}

}
