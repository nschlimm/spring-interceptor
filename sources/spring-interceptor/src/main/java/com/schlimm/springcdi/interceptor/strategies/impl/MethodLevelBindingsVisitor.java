package com.schlimm.springcdi.interceptor.strategies.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.MethodMetadata;

import com.schlimm.springcdi.interceptor.InterceptorModuleUtils;
import com.schlimm.springcdi.interceptor.model.InterceptorInfo;

public class MethodLevelBindingsVisitor implements InterceptorInfoVisitor {

	@SuppressWarnings("unchecked")
	@Override
	public void visit(InterceptorInfo interceptorInfo, BeanDefinitionHolder definition) {
		List<String> bindings = interceptorInfo.getInterceptorBindings();
		String bdName = definition.getBeanName();
		AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) definition.getBeanDefinition();
		for (String binding : bindings) {
			if (abd.getMetadata().hasAnnotatedMethods(binding)) {
				interceptorInfo.addInterceptedBean(bdName);
				Set<MethodMetadata> methods = abd.getMetadata().getAnnotatedMethods(binding);
				for (MethodMetadata methodMetadata : methods) {
					Set<Method> jlrMethods = InterceptorModuleUtils.getMethodsForName(InterceptorModuleUtils.getClass_forName(methodMetadata.getDeclaringClassName()), methodMetadata.getMethodName());
					for (Method method : jlrMethods) {
						if (AnnotationUtils.findAnnotation(method, InterceptorModuleUtils.getClass_forName(binding)) != null) {
							interceptorInfo.addInterceptedMethod(method);
						}
					}
				}
			}
		}
	}

}
