package com.applechip.core.util;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;

public class ArrayUtil extends ArrayUtils {
	public static <T> T[] sum(T[] clazz1, T[] clazz2) {
		T[] array = Arrays.copyOf(clazz1, clazz1.length + clazz2.length);
		System.arraycopy(clazz2, 0, array, clazz1.length, clazz2.length);
		return array;
	}
}
