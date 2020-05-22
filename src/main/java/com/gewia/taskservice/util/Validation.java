package com.gewia.taskservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validation {

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
