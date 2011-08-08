package com.schlimm.springcdi.interceptor.strategies.impl;

import org.springframework.beans.factory.config.BeanDefinitionHolder;

import com.schlimm.springcdi.interceptor.model.InterceptorInfo;

public interface InterceptorInfoVisitor {

	void visit(InterceptorInfo interceptorInfo, BeanDefinitionHolder definition);
	
}
