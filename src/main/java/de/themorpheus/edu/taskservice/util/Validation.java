package de.themorpheus.edu.taskservice.util;

public class Validation {

	public static boolean nullOrEmpty(String... values) {
		for (String value : values) if (value == null || value.isEmpty()) return true;
		return false;
	}

	public static boolean validateNull(Object... values) {
		for (Object value : values) if (value == null) return true;
		return false;
	}

	public static boolean validateNotNullOrEmpty(String value) {
		return validateNotNull(value) && !value.isEmpty();
	}

	public static boolean validateNotNull(Object value) {
		return value != null;
	}

	public static boolean greaterZero(long value) {
		return value > 0;
	}

	public static boolean lowerZero(long value) {
		return value < 0;
	}

    public static boolean greaterOrEqualZero(long value) {
		return value >= 0;
    }
}
