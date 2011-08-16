package com.schlimm.springcdi.interceptor.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Top level model bean that stored the {@link InterceptorInfo}s for all registered interceptors. This bean is registered with the
 * application context and can be injected.
 * 
 * @author Niklas Schlimm
 * 
 */
public class InterceptorMetaDataBean {

	/**
	 * All registered interceptors
	 */
	private List<InterceptorInfo> interceptors;

	public InterceptorMetaDataBean(List<InterceptorInfo> interceptors) {
		super();
		this.interceptors = interceptors;
	}

	/**
	 * Check if the bean is intercepted by any of the listed interceptors.
	 * 
	 * @param beanName
	 *            the given bean name
	 * 
	 * @return true, if the bean is intercepted
	 */
	public boolean isInterceptedBean(String beanName) {
		for (InterceptorInfo interceptor : interceptors) {
			if (interceptor.isInterceptingBean(beanName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return all interceptors that match the bean name (class level declarations) or the method (method level interceptor
	 * declarations).
	 * 
	 * @param beanName given bean name
	 * @param method given method name
	 * 
	 * @return all interceptors for the given method
	 */
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
