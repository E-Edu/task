package de.themorpheus.edu.taskservice.util;

public class Validation {

	public static boolean validateNotNullOrEmpty(String value) {
		return value != null && !value.isEmpty();
	}

	public static boolean greaterZero(long value) {
		return value > 0;
	}

}
