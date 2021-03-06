package de.themorpheus.edu.taskservice.endpoint;

import de.themorpheus.edu.taskservice.controller.user.UserDataController;
import de.themorpheus.edu.taskservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.annotation.Timed;

@Timed
@RestController
public class UserDataEndpoint {

	@Autowired private UserDataController userDataController;

	@GetMapping("task/user")
	public Object getUserData() {
		return this.userDataController.getUserData(Constants.UserId.TEST_UUID).getHttpResponse();
	}

	@DeleteMapping("task/user")
	public Object deleteUserData() {
		return this.userDataController.deleteUserData(Constants.UserId.TEST_UUID).getHttpResponse();
	}

}
