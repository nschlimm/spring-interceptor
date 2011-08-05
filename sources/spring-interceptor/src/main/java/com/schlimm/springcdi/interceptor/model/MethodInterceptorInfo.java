package com.schlimm.springcdi.interceptor.model;

import java.lang.reflect.Method;
import java.util.Set;

import javax.interceptor.AroundInvoke;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.MethodMetadata;

import com.schlimm.springcdi.interceptor.InterceptorModuleUtils;

@SuppressWarnings("rawtypes")
public class MethodInterceptorInfo extends InterceptorInfo {

	private final static String aroundInvokeAnnotationTypeName = AroundInvoke.class.getName();
	
	private final static Class aroundInvokeAnnotationType = AroundInvoke.class;
	
	public MethodInterceptorInfo(BeanDefinitionHolder beanDefinitionHolder) {
		super(beanDefinitionHolder);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void resolveInterceptorMethods() {
		for (MethodMetadata method : annotationMetadata.getAnnotatedMethods(aroundInvokeAnnotationTypeName)) {
			Set<Method> methods = InterceptorModuleUtils.getMethodsForName(InterceptorModuleUtils.getClass_forName(method.getDeclaringClassName()), method.getMethodName());
			for (Method jlrMethod : methods) {
				if (AnnotationUtils.findAnnotation(jlrMethod, aroundInvokeAnnotationType)!=null) {
					getInterceptorMethods().add(jlrMethod);
				}
			}
		}
	}

}
