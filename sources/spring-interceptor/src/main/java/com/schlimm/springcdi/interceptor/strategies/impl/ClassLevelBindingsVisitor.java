package com.schlimm.springcdi.interceptor.strategies.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.util.Assert;

import com.schlimm.springcdi.interceptor.InterceptorModuleUtils;
import com.schlimm.springcdi.interceptor.model.InterceptorInfo;

public class ClassLevelBindingsVisitor implements InterceptorInfoVisitor {

	@Override
	@SuppressWarnings({ "serial", "unchecked" })
	public void visit(InterceptorInfo interceptorInfo, BeanDefinitionHolder definition) {
		final List<String> bindings = interceptorInfo.getInterceptorBindings();
		List<String> unmatchedBindings = new ArrayList<String>() {{addAll(bindings);}};
		String bdName = definition.getBeanName();
		AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) definition.getBeanDefinition();
		for (String binding : bindings) {
			if (abd.getMetadata().hasAnnotation(binding)) {
				unmatchedBindings.remove(binding);
			}
			// scan stereotypes and interceptor bindings recursively to find binding
			if (scanAnnotations(InterceptorModuleUtils.getClass_forName(abd.getBeanClassName()), InterceptorModuleUtils.getClass_forName(binding))!=null) {
				unmatchedBindings.remove(binding);
			}
		}
		// if all declared bindings of the interceptor matched the bean, the interceptor applies on the class level
		if (unmatchedBindings.size()==0) {
			interceptorInfo.addInterceptedBean(bdName);
			interceptorInfo.addClassLevelInterception(bdName);
		}
	}

	public static <A extends Annotation> A scanAnnotations(Class<?> clazz, Class<A> annotationType) {
		Assert.notNull(clazz, "Class must not be null");
		A annotation = clazz.getAnnotation(annotationType);
		if (annotation != null) {
			return annotation;
		}
		for (Class<?> ifc : clazz.getInterfaces()) {
			annotation = scanAnnotations(ifc, annotationType);
			if (annotation != null) {
				return annotation;
			}
		}
		if (!Annotation.class.isAssignableFrom(clazz)) {
			for (Annotation ann : clazz.getAnnotations()) {
				if (InterceptorModuleUtils.isStereotype(ann.annotationType())||InterceptorModuleUtils.isInterceptorBinding(ann.annotationType())) {
					annotation = scanAnnotations(ann.annotationType(), annotationType);
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
		return scanAnnotations(superClass, annotationType);
	}

}
