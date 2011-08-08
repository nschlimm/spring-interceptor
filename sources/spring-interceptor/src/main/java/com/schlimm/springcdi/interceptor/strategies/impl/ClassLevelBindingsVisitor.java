package com.schlimm.springcdi.interceptor.strategies.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;

import com.schlimm.springcdi.interceptor.model.InterceptorInfo;

public class ClassLevelBindingsVisitor implements InterceptorInfoVisitor {

	@Override
	public void visit(InterceptorInfo interceptorInfo, BeanDefinitionHolder definition) {
		List<String> bindings = interceptorInfo.getInterceptorBindings();
		String bdName = definition.getBeanName();
		AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) definition.getBeanDefinition();
		for (String binding : bindings) {
			if (abd.getMetadata().hasAnnotation(binding)) {
				interceptorInfo.addInterceptedBean(bdName);
				interceptorInfo.addClassLevelInterception(bdName);
			}
			// rekursiv: wenn eine Annotation meta @InterceptorBinding ist, dann prüfe ob binding als meta annotation binding hat
		}
	}

}
