package com.schlimm.springcdi.interceptor.strategies.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.core.annotation.AnnotationUtils;

import com.schlimm.springcdi.interceptor.InterceptorModuleUtils;
import com.schlimm.springcdi.interceptor.model.InterceptorInfo;

/**
 * Check is the given {@link BeanDefinition} is intercepted by the given interceptor. If yes store that information in
 * {@link InterceptorInfo}. Assumes that {@link BeanDefinition} is of type {@link AnnotatedBeanDefinition}.
 * 
 * @author Niklas Schlimm
 * 
 */
public class ClassLevelBindingsVisitor extends InterceptorInfoVisitor {

	@Override
	@SuppressWarnings({ "serial", "unchecked" })
	public void visit(InterceptorInfo interceptorInfo, BeanDefinitionHolder definition) {
		final List<String> bindings = interceptorInfo.getInterceptorBindings();
		List<String> unmatchedBindings = new ArrayList<String>() {
			{
				addAll(bindings);
			}
		};
		String bdName = definition.getBeanName();
		AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) definition.getBeanDefinition();
		for (String binding : bindings) {
			// scan stereotypes and interceptor bindings recursively to find binding
			Annotation beanAnnotation = findBindingAnnotation(InterceptorModuleUtils.getClass_forName(abd.getBeanClassName()), InterceptorModuleUtils.getClass_forName(binding));
			if (beanAnnotation != null) { // true=bean declares this interceptor binding
				Map<String, Object> beanAnnotationAttributes = AnnotationUtils.getAnnotationAttributes(beanAnnotation);
				Map<String, Object> interceptorAttributes = interceptorInfo.getAnnotationAttributes(binding);
				if (interceptorAttributes != null && !interceptorAttributes.isEmpty()) {
					if (InterceptorInfoVisitor.matchBindingTypeMembers(beanAnnotation, beanAnnotationAttributes, interceptorAttributes)) {
						unmatchedBindings.remove(binding);
					}
				} else {
					unmatchedBindings.remove(binding);
				}
			}
		}
		// if all declared bindings of the interceptor matched the bean, the interceptor applies on the class level
		if (unmatchedBindings.isEmpty()) {
			interceptorInfo.addInterceptedBean(bdName);
			interceptorInfo.addClassLevelInterception(bdName);
		}
	}

}
