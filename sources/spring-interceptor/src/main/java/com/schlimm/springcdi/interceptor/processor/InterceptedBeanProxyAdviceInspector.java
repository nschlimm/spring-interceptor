package com.schlimm.springcdi.interceptor.processor;

/**
 * Infrastructure Interface to retrieve the {@link InterceptedBeanProxyAdvice} from an intercepted bean.
 * 
 * @author Niklas Schlimm
 *
 */
public interface InterceptedBeanProxyAdviceInspector {

	Object getInterceptorTarget();
	InterceptedBeanProxyAdvice getInterceptor();
	
}
