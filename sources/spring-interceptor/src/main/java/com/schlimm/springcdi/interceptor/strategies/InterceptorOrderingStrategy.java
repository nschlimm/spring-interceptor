package com.schlimm.springcdi.interceptor.strategies;

import java.util.List;

import org.springframework.beans.factory.BeanFactory;

import com.schlimm.springcdi.interceptor.InterceptorAwareBeanFactoryPostProcessor;
import com.schlimm.springcdi.interceptor.model.InterceptorInfo;

/**
 * Strategy to order interceptors. Clients can implement their own custom ordering strategy.
 * 
 * @author Niklas Schlimm
 *
 */
public interface InterceptorOrderingStrategy {

	/**
	 * Order the interceptors.
	 * 
	 * @param beanFactory the factory that defines the interceptors
	 * @param interceptors the registered interceptors 
	 * @param configuredOrder the configured order of {@link InterceptorAwareBeanFactoryPostProcessor} (if any)
	 * 
	 * @return the ordered list of interceptors
	 */
	@SuppressWarnings("rawtypes")
	List<InterceptorInfo> orderInterceptors(BeanFactory beanFactory, List<InterceptorInfo> interceptors, List<Class> configuredOrder);

}
