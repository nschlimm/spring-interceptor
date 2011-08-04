package com.schlimm.springcdi.interceptor.model;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class InterceptorMetaDataBean {
	
	private Set<InterceptorInfo> interceptors;

	public void setInterceptors(Set<InterceptorInfo> interceptors) {
		this.interceptors = interceptors;
	}

	public Set<InterceptorInfo> getInterceptors() {
		return interceptors;
	}
	
	public boolean isInterceptedBean(String beanName) {
		for (InterceptorInfo interceptor : interceptors) {
			if (interceptor.isInterceptingBean(beanName)) {
				return true;
			}
		}
		return false;
	}

	public List<InterceptorInfo> getMatchingInterceptors(String beanName, Method method) {
		// get all class level interceptors for bean name
		// get all method level interceptors for method
		return null;
	}

}
