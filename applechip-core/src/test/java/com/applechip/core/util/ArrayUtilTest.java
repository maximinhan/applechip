package com.applechip.core.util;

import org.junit.Test;

public class ArrayUtilTest {

	@Test
	public void testSum() throws Exception {
		String[] array1 = { "111", "222", "333" };
		String[] array2 = { "444", "555", "666" };
		String[] result = ArrayUtil.sum(array1, array2);
		for (int i = 0; i < result.length; i++)
			System.out.println(result[i]);
	}

}
