package com.schlimm.springcdi.interceptor.processor;

public interface InterceptedBeanProxyAdviceInspector {

	Object getInterceptorTarget();
	InterceptedBeanProxyAdvice getInterceptor();
	
}
