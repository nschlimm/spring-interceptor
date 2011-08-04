package com.schlimm.springcdi.interceptor.model;

import javax.interceptor.AroundInvoke;

import org.springframework.beans.factory.config.BeanDefinitionHolder;

public class MethodInterceptorInfo extends InterceptorInfo {

	private final static String aroundInvokeAnnotationType = AroundInvoke.class.getName();
	
	public MethodInterceptorInfo(BeanDefinitionHolder beanDefinitionHolder) {
		super(beanDefinitionHolder);
	}

	@Override
	protected void resolveInterceptorMethods() {
		interceptorMethods = annotationMetadata.getAnnotatedMethods(aroundInvokeAnnotationType);
	}

}
