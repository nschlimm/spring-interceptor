package com.schlimm.springcdi.interceptor.strategies.impl;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.schlimm.springcdi.interceptor.model.MethodInterceptorInfo;


@ContextConfiguration("/test-context-interceptor-simple_methodlevel.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class SimpleInterceptorResolutionStrategyTest_methodlevel {

	@Autowired
	private BeanFactory beanFactory;
	
	@Test
	public void testInterceptorResolutionSimple_Count() {
		Assert.assertTrue(new SimpleInterceptorResolutionStrategy().resolveRegisteredInterceptors(beanFactory).size()==1);
	}
	
	@Test
	public void testCoundInterceptedBeans() {
		Assert.assertTrue(new SimpleInterceptorResolutionStrategy().resolveRegisteredInterceptors(beanFactory).get(0).getInterceptedBeans().size()==2);
	}

	/**
	 * Class level interceptor bindings apply to all methods -> all methods listed in intercepted methods
	 */
	@Test
	public void testCountInterceptedBusinessMethods() {
		Assert.assertTrue(((MethodInterceptorInfo)new SimpleInterceptorResolutionStrategy().resolveRegisteredInterceptors(beanFactory).get(0)).getInterceptedMethods().size()==14);
	}
	
	/**
	 * One class level interceptor binding -> one class level interception
	 */
	@Test
	public void testCountClassLevelInterceptions() {
		Assert.assertTrue(((MethodInterceptorInfo)new SimpleInterceptorResolutionStrategy().resolveRegisteredInterceptors(beanFactory).get(0)).getClassLevelInterceptions().size()==1);
	}
}
