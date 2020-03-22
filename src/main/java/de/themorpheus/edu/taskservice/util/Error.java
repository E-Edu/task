package de.themorpheus.edu.taskservice.util;

import lombok.Data;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Data
public class Error {

	public static final Error FORBIDDEN = new Error(HttpStatus.FORBIDDEN, i18n.FORBIDDEN);
	public static final Error INVALID_PARAM = new Error(BAD_REQUEST, i18n.INVALID_PARAM);
	public static final Error MISSING_PARAM = new Error(BAD_REQUEST, i18n.MISSING_PARAM);
	public static final Error NOT_FOUND = new Error(HttpStatus.NOT_FOUND, i18n.NOT_FOUND);
	public static final Error SERVER_ERROR = new Error(HttpStatus.FORBIDDEN, i18n.SERVER_ERROR);
	public static final Error UNAUTHORIZED = new Error(HttpStatus.UNAUTHORIZED, i18n.UNAUTHORIZED);

	private final int status;
	private final String messageKey;
	private final String extra;

	public Error(HttpStatus status, String messageKey) {
		this(status, messageKey, null);
	}

	public Error(HttpStatus status, String messageKey, String extra) {
		this(status.value(), messageKey, extra);
	}

	private Error(int status, String messageKey, String extra) {
		this.status = status;
		this.messageKey = messageKey;
		this.extra = extra;
	}

	public Error copyWithExtra(String extra) {
		return new Error(this.getStatus(), this.getMessageKey(), extra);
	}

}
