package com.schlimm.springcdi.interceptor.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class InterceptorMetaDataBean {
	
	private List<InterceptorInfo> interceptors;
	
	public InterceptorMetaDataBean(List<InterceptorInfo> interceptors) {
		super();
		this.interceptors = interceptors;
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
		List<InterceptorInfo> matchingInterceptors = new ArrayList<InterceptorInfo>();
		for (InterceptorInfo interceptorInfo : interceptors) {
				if (interceptorInfo.matches(beanName, method)) {
					matchingInterceptors.add(interceptorInfo);
				}
		}
		return matchingInterceptors;
	}

	public List<InterceptorInfo> getInterceptors() {
		return interceptors;
	}

	public void setInterceptors(List<InterceptorInfo> interceptors) {
		this.interceptors = interceptors;
	}

}
