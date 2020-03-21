package de.themorpheus.edu.taskservice.util;

public class Validation {

	public static boolean validateNotNullOrEmpty(String value) {
		return validateNotNull(value) && !value.isEmpty();
	}

	public static boolean validateNotNull(Object value) {
		return value != null;
	}

}
