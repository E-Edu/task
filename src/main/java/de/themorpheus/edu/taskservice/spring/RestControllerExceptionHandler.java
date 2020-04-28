package de.themorpheus.edu.taskservice.spring;

import de.themorpheus.edu.taskservice.util.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger EXCEPTION_LOGGER = LoggerFactory.getLogger(RestControllerExceptionHandler.class.getSimpleName());

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> exception(Exception exception) {
		EXCEPTION_LOGGER.error("Caught unhandled error reaching the end of the endpoint pipeline", exception);

		Error error = Error.INTERNAL_SERVER_ERROR;
		String extra = "An internal server error occurred!";
		error = error.copyWithExtra(extra);

		return ResponseEntity.status(error.getStatus()).body(error.getBody());
	}

}
