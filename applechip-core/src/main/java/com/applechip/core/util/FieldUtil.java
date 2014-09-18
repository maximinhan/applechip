package com.applechip.core.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.reflect.FieldUtils;

@Slf4j
public class FieldUtil extends FieldUtils {
	@SuppressWarnings("unchecked")
	public static <T> T readField(Field field, Class<T> clazz) {
		return (T) readField(field);
	}

	public static <T> List<T> readFieldList(Class<?> clazz, Class<T> result) {
		List<T> list = new ArrayList<T>();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.getType().isAssignableFrom(result)) {
				list.add(readField(field, result));
			}
		}
		return list;
	}

	public static Object readField(Field field) {
		Object object = null;
		try {
			object = readField(field, field, Boolean.TRUE);
		}
		catch (IllegalAccessException e) {
			log.error("readField fail... field: {}, message: {}", field, e.getMessage());
		}
		catch (NullPointerException e) {
			log.error("readField fail... field: {}, message: {}", field, e.getMessage());
		}
		return object;
	}
}
