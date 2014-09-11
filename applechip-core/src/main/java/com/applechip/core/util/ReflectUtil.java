package com.applechip.core.util;

import java.lang.reflect.Field;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.reflect.FieldUtils;

@Slf4j
public class ReflectUtil {

	public static Object readField(Field field, Class<?>... classes) {
		Object object = null;
		try {
			if (ArrayUtils.contains(classes, field.getType())) {
				object = FieldUtils.readField(field, field, Boolean.TRUE);
			}
		}
		catch (IllegalAccessException e) {
			log.error("readField fail... field: {}, classes: {}, message: {}", field, classes, e.getMessage());
		}
		catch (NullPointerException e) {
			log.error("readField fail... field: {}, classes: {}, message: {}", field, classes, e.getMessage());
		}
		return object;
	}

	public static Object readField(Field field) {
		Object object = null;
		try {
			object = FieldUtils.readField(field, field, Boolean.TRUE);
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
