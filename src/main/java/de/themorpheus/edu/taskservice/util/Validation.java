package de.themorpheus.edu.taskservice.util;

public class Validation {

	private Validation() {
	}

	public static boolean validateNotNullOrEmpty(String value) {
		return value != null && !value.isEmpty();
	}

	public static boolean greaterZero(long value) {
		return value > 0;
	}

	public static boolean greaterOne(long value) {
		return value > 1;
	}

}
