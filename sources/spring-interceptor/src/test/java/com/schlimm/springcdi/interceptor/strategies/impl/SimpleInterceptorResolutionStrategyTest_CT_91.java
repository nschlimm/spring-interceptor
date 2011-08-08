package com.schlimm.springcdi.interceptor.strategies.impl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.schlimm.springcdi.interceptor.model.InterceptorInfo;
import com.schlimm.springcdi.interceptor.model.MethodInterceptorInfo;


@ContextConfiguration("/test-context-interceptor-ct-91-C1.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class SimpleInterceptorResolutionStrategyTest_CT_91 {

	@Autowired
	private BeanFactory beanFactory;

	private InterceptorInfo dataAccessInterceptor;
	
	private InterceptorInfo transactionInterceptor;

	private SimpleInterceptorResolutionStrategy strategy = new SimpleInterceptorResolutionStrategy();
	
	@Before
	public void setUp() {
		dataAccessInterceptor = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory)beanFactory).getBeanDefinition("dataAccessInterceptor"), "dataAccessInterceptor"));
		transactionInterceptor = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory)beanFactory).getBeanDefinition("transactionInterceptor"), "transactionInterceptor"));
	}
	
	@Test
	public void testResolveInterceptorTargets_DataaccessInterceptor() {
		Assert.assertTrue(strategy.resolveInterceptorTargets(beanFactory, dataAccessInterceptor).isInterceptingBean("CT91_TrialService_Impl2"));
	}
	
	@Test
	public void testResolveInterceptorTargets_TransactionInterceptor() {
		Assert.assertTrue(strategy.resolveInterceptorTargets(beanFactory, transactionInterceptor).isInterceptingBean("CT91_TrialService_Impl2"));
	}
	
}
