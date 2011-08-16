package com.schlimm.springcdi.interceptor.strategies.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import javax.enterprise.inject.Stereotype;
import javax.enterprise.util.Nonbinding;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.Assert;

import com.schlimm.springcdi.interceptor.model.InterceptorInfo;

/**
 * Super class for all classes that manipulate {@link InterceptorInfo} based on {@link BeanDefinition} info. Clients can implement
 * their own visitor to cope with different types of {@link BeanDefinition}, such as {@link AnnotatedBeanDefinition} or
 * {@link GenericBeanDefinition}.
 * 
 * @author Niklas Schlimm
 * 
 */
abstract class InterceptorInfoVisitor {

	abstract void visit(InterceptorInfo interceptorInfo, BeanDefinitionHolder definition);

	public static boolean matchBindingTypeMembers(Annotation beanAnnotation, Map<String, Object> beanAnnotationAttributes, Map<String, Object> interceptorAttributes) {
		for (Method method : beanAnnotation.annotationType().getDeclaredMethods()) {
			if (method.isAnnotationPresent(Nonbinding.class)) {
				beanAnnotationAttributes.remove(method.getName());
				interceptorAttributes.remove(method.getName());
			}
		}
		if (beanAnnotationAttributes.values().size() == interceptorAttributes.values().size()) {
			for (String key : beanAnnotationAttributes.keySet()) {
				if (!beanAnnotationAttributes.get(key).equals(interceptorAttributes.get(key)))
					return false;
			}
		} else {
			return false;
		}
		return true;
	}

	public static <A extends Annotation> A findBindingAnnotation(Class<?> clazz, Class<A> annotationType) {
		Assert.notNull(clazz, "Class must not be null");
		A annotation = clazz.getAnnotation(annotationType);
		if (annotation != null) {
			return annotation;
		}
		for (Class<?> ifc : clazz.getInterfaces()) {
			annotation = findBindingAnnotation(ifc, annotationType);
			if (annotation != null) {
				return annotation;
			}
		}
		if (!Annotation.class.isAssignableFrom(clazz)) {
			for (Annotation ann : clazz.getAnnotations()) {
				if (isStereotype(ann.annotationType()) || isInterceptorBinding(ann.annotationType())) {
					annotation = findBindingAnnotation(ann.annotationType(), annotationType);
					if (annotation != null) {
						return annotation;
					}
				}
			}
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass == null || superClass == Object.class) {
			return null;
		}
		return findBindingAnnotation(superClass, annotationType);
	}

	/**
	 * Check if annotation is an interceptor binding.
	 * 
	 * @param candidate
	 * @return
	 */
	public static boolean isInterceptorBinding(Class<? extends Annotation> candidate) {
		for (Annotation annotation : candidate.getAnnotations()) {
			if (annotation.annotationType().equals(InterceptorInfo.interceptorBindingAnnotationType)) {
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
