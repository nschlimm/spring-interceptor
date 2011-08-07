package com.schlimm.springcdi.interceptor.processor;

public interface InterceptorProxyInspector {

	Object getInterceptorTarget();
	InterceptedBeanProxyAdvice getInterceptor();
	
}
