package com.applechip.core.basic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ReflectionUtils {

	/**
	 * <pre>
	 * invokeMethod
	 * private method 테스트를 위한 util
	 * 
	 * <pre>
	 * @param obj
	 * @param methodName
	 * @param returnType
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T invokeMethod(Class<?> clazz, String methodName, Class<T> returnType, Object... args) {
		Class<?>[] parameters = new Class<?>[args.length];
		for (int i = 0; i < args.length; i++) {
			parameters[i] = args[i].getClass();
		}
		try {
			Object obj = clazz.newInstance();
			Method method = obj.getClass().getDeclaredMethod(methodName, parameters);
			method.setAccessible(true);
			return (T) method.invoke(obj, args);
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Test
	public void accessPrivateMethodTest1() {
		assertThat("namkyu", is(ReflectionUtils.invokeMethod(UserService.class, "getUser", String.class, "namkyu")));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void accessPrivateMethodTest2() {
		List<String> list = ReflectionUtils.invokeMethod(UserService.class, "getUsers", List.class, "namkyu");
		assertThat("kyu", is(list.get(0)));
		assertThat("kyu2", is(list.get(1)));
	}

}

class UserService {

	public String getUser(String userName) {
		return makeUser(userName);
	}

	private String makeUser(String userName) {
		return userName;
	}

	public List<String> getUsers(String userName) {
		return makeUserList();
	}

	private List<String> makeUserList() {
		List<String> list = new ArrayList<String>();
		list.add("kyu");
		list.add("kyu2");
		return list;
	}
}