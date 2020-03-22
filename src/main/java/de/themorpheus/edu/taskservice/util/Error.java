package de.themorpheus.edu.taskservice.util;

import lombok.Data;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Data
public class Error {

	public static final Error FORBIDDEN = new Error(HttpStatus.FORBIDDEN, i18n.FORBIDDEN);
	public static final Error INVALID_PARAM = new Error(BAD_REQUEST, i18n.INVALID_PARAM);
	public static final Error MISSING_PARAM = new Error(BAD_REQUEST, i18n.MISSING_PARAM);
	public static final Error SERVER_ERROR = new Error(HttpStatus.FORBIDDEN, i18n.SERVER_ERROR);
	public static final Error UNAUTHORIZED = new Error(HttpStatus.UNAUTHORIZED, i18n.UNAUTHORIZED);

	private final int status;
	private final String messageKey;

	public Error(HttpStatus status, String messageKey) {
		this.status = status.value();
		this.messageKey = messageKey;
	}

}
