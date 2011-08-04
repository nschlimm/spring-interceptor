package com.schlimm.springcdi.interceptor.strategies;

import javax.interceptor.InvocationContext;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.schlimm.springcdi.interceptor.model.InterceptorMetaDataBean;

public interface InterceptorChainingStrategy {
	
	InvocationContext getChainedInvocationContexts(ConfigurableListableBeanFactory beanFactory, InterceptorMetaDataBean metaData, String targetBeanName, Object targetBean);

}
