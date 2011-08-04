package com.schlimm.springcdi.interceptor.strategies.impl;

import javax.interceptor.InvocationContext;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.schlimm.springcdi.interceptor.model.InterceptorMetaDataBean;
import com.schlimm.springcdi.interceptor.strategies.InterceptorChainingStrategy;

public class SimpleInterceptorChainingStrategy implements InterceptorChainingStrategy {

	@Override
	public InvocationContext getChainedInvocationContexts(ConfigurableListableBeanFactory beanFactory, InterceptorMetaDataBean metaData, String targetBeanName, Object targetBean) {
		// TODO Auto-generated method stub
		return null;
	}

}
