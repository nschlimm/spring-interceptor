package com.schlimm.springcdi.interceptor.strategies.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration("/test-context-interceptor-simple.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class SimpleInterceptorResolutionStrategyTest {

	@Autowired
	private BeanFactory beanFactory;
	
	@Test
	public void testInterceptorResolutionSimple_Count() {
		Assert.assertTrue(new SimpleInterceptorResolutionStrategy().resolveRegisteredInterceptors(beanFactory).size()==1);
	}
	
	@Test
	public void testInterceptorsTargetBeanInfo() {
		Assert.assertTrue(new SimpleInterceptorResolutionStrategy().resolveRegisteredInterceptors(beanFactory).get(0).getInterceptedBeans().size()==1);
	}
}
