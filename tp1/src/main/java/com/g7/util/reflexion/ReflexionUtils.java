package com.g7.util.reflexion;

import java.lang.reflect.Method;

public class ReflexionUtils {


	/**
	 * Obtiene el metodo a partir del nombre de la clase y del método.
	 * @param clazz
	 * @param methodName
	 * @return
	 */
	public static Method getMethod(Class<?> clazz, String methodName) {
		Method[] methods = clazz.getMethods();
		for(Method method : methods) {
			if(method.getName().equals(methodName)) {
				return method;
			}
		}
		
		return null;
	}
}
