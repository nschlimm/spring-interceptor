package com.schlimm.springcdi.interceptor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.schlimm.springcdi.interceptor.strategies.InterceptorResolutionStrategy;

public class InterceptorAwareBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@SuppressWarnings("unused")
	private InterceptorResolutionStrategy interceptorResolutionStrategy;
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
	}

}
