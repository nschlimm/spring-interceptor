package com.schlimm.springcdi.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.Stereotype;
import javax.interceptor.InterceptorBinding;

import org.springframework.util.Assert;

@SuppressWarnings("rawtypes")
public class InterceptorModuleUtils {

	public static Class getClass_forName_Safe(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return clazz;
		} catch (ClassNotFoundException e) {
			return null;
		}
	}
	
	public static Class getClass_forName(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return clazz;
		} catch (ClassNotFoundException e) {
			throw new InterceptorAwareBeanFactoryPostProcessorException("Could not find class with name: " + className, e);
		}
	}
	
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

	public static boolean isInterceptorBinding(Class<? extends Annotation> candidate) {
		for (Annotation annotation : candidate.getAnnotations()) {
			if (annotation.annotationType().equals(InterceptorBinding.class)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isStereotype(Class<? extends Annotation> candidate) {
		for (Annotation annotation : candidate.getAnnotations()) {
			if (annotation.annotationType().equals(Stereotype.class)) {
				return true;
			}
		}
		return false;
	}

}
