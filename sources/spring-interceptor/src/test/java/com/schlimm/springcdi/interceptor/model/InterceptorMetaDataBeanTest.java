package com.schlimm.springcdi.interceptor.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.schlimm.springcdi.interceptor.simple.Simple_MyServiceInterface_Impl;
import com.schlimm.springcdi.interceptor.simple_methodlevel.Simple_MyServiceInterface_Impl2;
import com.schlimm.springcdi.interceptor.strategies.impl.SimpleInterceptorResolutionStrategy;


@ContextConfiguration("/test-context-interceptor-simple_methodlevel.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class InterceptorMetaDataBeanTest {

	@Autowired
	private BeanFactory beanFactory;
	
	private SimpleInterceptorResolutionStrategy interceptorResolutionStrategy;

	private InterceptorMetaDataBean metaDataBean;
	
	@Before
	public void setUp() {
		interceptorResolutionStrategy = new SimpleInterceptorResolutionStrategy();
		metaDataBean = new InterceptorMetaDataBean(interceptorResolutionStrategy.resolveRegisteredInterceptors(beanFactory));
	}
	
	@Test
	public void testGetMatchingInterceptors_beanName() throws SecurityException, NoSuchMethodException {
		Assert.assertTrue(metaDataBean.getMatchingInterceptors("simple_MyServiceInterface_Impl", this.getClass().getDeclaredMethods()[0]).size()==1);
	}
	
	@Test
	public void testGetMatchingInterceptors_methodName() throws SecurityException, NoSuchMethodException {
		Assert.assertTrue(metaDataBean.getMatchingInterceptors("", Simple_MyServiceInterface_Impl2.class.getDeclaredMethod("sayHello", new Class[]{})).size()==1);
	}
	
	@Test
	public void testGetMatchingInterceptors_beanName_negative() throws SecurityException, NoSuchMethodException {
		Assert.assertTrue(metaDataBean.getMatchingInterceptors("simple_MyServiceInterface_Impl2", this.getClass().getDeclaredMethods()[0]).size()==0);
	}
	
	@Test
	public void testGetMatchingInterceptors_methodName_negative() throws SecurityException, NoSuchMethodException {
		Assert.assertTrue(metaDataBean.getMatchingInterceptors("", Simple_MyServiceInterface_Impl.class.getDeclaredMethod("sayHello", new Class[]{})).size()==0);
	}
	
}
