package com.schlimm.springcdi.interceptor;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.Assert;

/**
 * Re-used utility methods.
 * 
 * @author Niklas Schlimm
 *
 */
@SuppressWarnings("rawtypes")
public class InterceptorModuleUtils {

	/**
	 * Friendly class for name implementation.
	 * 
	 * @param className class to find
	 * @return class found, null if no class was found
	 */
	public static Class getClass_forName_Safe(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return clazz;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * Convenient class for name implementation.
	 * 
	 * @param className Class to find
	 * @return Class found
	 */
	public static Class getClass_forName(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return clazz;
		} catch (ClassNotFoundException e) {
			throw new InterceptorAwareBeanFactoryPostProcessorException("Could not find class with name: " + className, e);
		}
	}
	
	/**
	 * Return all methods with given name.
	 * 
	 * @param clazz class scanned
	 * @param methodName method name to search
	 * @return methods found, empty {@link HashSet} if non found
	 */
	public static Set<Method> getMethodsForName(Class<?> clazz, String methodName) {
		Assert.notNull(clazz, "Class must not be null");
		Assert.notNull(methodName, "Method name must not be null");
		Set<Method> methods = new HashSet<Method>();
		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (Method method : declaredMethods) {
			if (methodName.equals(method.getName())) {
				methods.add(method);
			}
		}
		Class<?>[] ifcs = clazz.getInterfaces();
		for (Class<?> ifc : ifcs) {
			methods.addAll(getMethodsForName(ifc, methodName));
		}
		if (clazz.getSuperclass() != null) {
			methods.addAll(getMethodsForName(clazz.getSuperclass(), methodName));
		}
		return methods;
	}

}
