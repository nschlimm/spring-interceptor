package com.schlimm.springcdi.interceptor.model;

import java.lang.reflect.Method;
import java.util.List;

public class InterceptedBusinessMethod {
	
	private Method interceptedMethod;
	
	private List<InterceptorInfo> interceptors;

	public Method getInterceptedMethod() {
		return interceptedMethod;
	}

	public void setInterceptedMethod(Method interceptedMethod) {
		this.interceptedMethod = interceptedMethod;
	}

	public List<InterceptorInfo> getInterceptors() {
		return interceptors;
	}

	public void setInterceptors(List<InterceptorInfo> interceptors) {
		this.interceptors = interceptors;
	}

}
