package com.schlimm.springcdi.interceptor.strategies;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;

import com.schlimm.springcdi.interceptor.model.InterceptorInfo;

public interface InterceptorOrderingStrategy {

	@SuppressWarnings("rawtypes")
	List<InterceptorInfo> orderInterceptors(BeanFactory beanFactory, List<InterceptorInfo> interceptors, List<Class> configuredOrder);

}
