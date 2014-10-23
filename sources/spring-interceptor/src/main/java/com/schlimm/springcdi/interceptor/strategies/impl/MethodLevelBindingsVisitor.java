package com.schlimm.springcdi.interceptor.strategies.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.core.annotation.AnnotationUtils;

import com.schlimm.springcdi.interceptor.InterceptorModuleUtils;
import com.schlimm.springcdi.interceptor.model.InterceptorInfo;

/**
 * Check is the given {@link BeanDefinition} is intercepted by the given interceptor on the method level. If yes store that
 * information in {@link InterceptorInfo}. Assumes that {@link BeanDefinition} is of type {@link AnnotatedBeanDefinition}.
 * 
 * @author Niklas Schlimm
 * 
 */
public class MethodLevelBindingsVisitor extends InterceptorInfoVisitor {

	@Override
	public void visit(InterceptorInfo interceptorInfo, BeanDefinitionHolder definition) {
		AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) definition.getBeanDefinition();
		Set<Method> interceptedMethods = resolveInterceptedMethodsWithBinding(abd, interceptorInfo);
		if (!interceptedMethods.isEmpty()) {
			interceptorInfo.addInterceptedBean(definition.getBeanName());
		}
		interceptorInfo.getInterceptedMethods().addAll(interceptedMethods);
	}

	@SuppressWarnings({ "unchecked", "serial" })
	private Set<Method> resolveInterceptedMethodsWithBinding(AnnotatedBeanDefinition abd, final InterceptorInfo interceptorInfo) {
		final List<String> bindings = interceptorInfo.getInterceptorBindings();
		Set<Method> interceptedMethods = new HashSet<Method>();
		Method[] jlrMethods = InterceptorModuleUtils.getClass_forName(abd.getBeanClassName()).getMethods();
		for (Method method : jlrMethods) {
			List<String> unmatchedBindings = new ArrayList<String>() {
				{
					addAll(bindings);
				}
			};
			for (String binding : bindings) {
				Annotation methodAnnotation = AnnotationUtils.findAnnotation(method, InterceptorModuleUtils.getClass_forName(binding));
				Map<String, Object> interceptorAttributes = interceptorInfo.getAnnotationAttributes(binding);
				if (methodAnnotation != null) {
					Map<String, Object> methodAnnotationAttributes = AnnotationUtils.getAnnotationAttributes(methodAnnotation);
					if (interceptorAttributes != null && !interceptorAttributes.isEmpty()) {
						if (InterceptorInfoVisitor.matchBindingTypeMembers(methodAnnotation, methodAnnotationAttributes, interceptorAttributes)) {
							unmatchedBindings.remove(binding);
						}
					} else {
						unmatchedBindings.remove(binding);
					}
				}
				// Class level annotations apply to all methods
				Annotation beanAnnotation = InterceptorInfoVisitor.findBindingAnnotation(InterceptorModuleUtils.getClass_forName(abd.getBeanClassName()),
                        InterceptorModuleUtils.getClass_forName(binding));
				if (beanAnnotation != null) { // true=bean declares this interceptor binding
					Map<String, Object> beanAnnotationAttributes = AnnotationUtils.getAnnotationAttributes(beanAnnotation);
					if (interceptorAttributes != null && !interceptorAttributes.isEmpty()) {
						if (InterceptorInfoVisitor.matchBindingTypeMembers(beanAnnotation, beanAnnotationAttributes, interceptorAttributes)) {
							unmatchedBindings.remove(binding);
						}
					} else {
						unmatchedBindings.remove(binding);
					}
				}
			}
			if (unmatchedBindings.isEmpty()) {
				interceptedMethods.add(method);
			}
		}
		return interceptedMethods;
	}

}
