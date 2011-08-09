package com.schlimm.springcdi.interceptor.strategies.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.core.annotation.AnnotationUtils;

import com.schlimm.springcdi.interceptor.InterceptorModuleUtils;
import com.schlimm.springcdi.interceptor.model.InterceptorInfo;

public class MethodLevelBindingsVisitor implements InterceptorInfoVisitor {

	@Override
	public void visit(InterceptorInfo interceptorInfo, BeanDefinitionHolder definition) {
		final List<String> bindings = interceptorInfo.getInterceptorBindings();
		AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) definition.getBeanDefinition();
		Set<Method> interceptedMethods = resolveInterceptedMethodsWithBinding(abd, bindings);
		if (interceptedMethods.size()>0) {
			interceptorInfo.addInterceptedBean(definition.getBeanName());
		}
		interceptorInfo.getInterceptedMethods().addAll(interceptedMethods);
	}

	@SuppressWarnings({ "unchecked", "serial" })
	private Set<Method> resolveInterceptedMethodsWithBinding(AnnotatedBeanDefinition abd, final List<String> bindings) {
		Set<Method> interceptedMethods = new HashSet<Method>();
		Method[] jlrMethods = InterceptorModuleUtils.getClass_forName(abd.getBeanClassName()).getMethods();
		for (Method method : jlrMethods) {
			List<String> unmatchedBindings = new ArrayList<String>() {{addAll(bindings);}};
			for (String binding : bindings) {
				if (AnnotationUtils.findAnnotation(method, InterceptorModuleUtils.getClass_forName(binding)) != null) {
					unmatchedBindings.remove(binding);
				}
			}
			if (unmatchedBindings.size()==0) {
				interceptedMethods.add(method);
			}
		}
		return interceptedMethods;
	}

}
