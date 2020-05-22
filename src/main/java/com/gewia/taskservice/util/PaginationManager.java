package com.gewia.taskservice.util;

public class PaginationManager {

	public static int checkPositiveToMaxOrGetMax(int value, int max) {
		if (value < 0 || value > max) value = max;

		return value;
	}

}
