package com.schlimm.springcdi.interceptor.processor;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.schlimm.springcdi.interceptor.model.InterceptorMetaDataBean;
import com.schlimm.springcdi.interceptor.simple.Simple_MyServiceInterface_Impl;
import com.schlimm.springcdi.interceptor.strategies.impl.SimpleInterceptorResolutionStrategy;

@ContextConfiguration("/test-context-interceptor-simple_methodlevel.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class InterceptorAwareBeanPostProcessorTest {
	
	@Autowired
	private BeanFactory beanFactory;
	
	private InterceptorAwareBeanPostProcessor processor;
	
	@Before
	public void setUp() throws Exception {
		processor = new InterceptorAwareBeanPostProcessor(new InterceptorMetaDataBean(new SimpleInterceptorResolutionStrategy().resolveRegisteredInterceptors(beanFactory)), (ConfigurableListableBeanFactory) beanFactory);
	}

	@Test
	public void testProxying() throws Throwable {
		Simple_MyServiceInterface_Impl simple_MyServiceInterface_Impl = (Simple_MyServiceInterface_Impl) processor.postProcessAfterInitialization(beanFactory.getBean("simple_MyServiceInterface_Impl"), "simple_MyServiceInterface_Impl");
		Assert.assertTrue(AopUtils.isCglibProxy(simple_MyServiceInterface_Impl));
	}

	@Test
	public void testWorkingInterceptor() throws Throwable {
		Simple_MyServiceInterface_Impl simple_MyServiceInterface_Impl = (Simple_MyServiceInterface_Impl) processor.postProcessAfterInitialization(beanFactory.getBean("simple_MyServiceInterface_Impl"), "simple_MyServiceInterface_Impl");
		Assert.assertTrue(simple_MyServiceInterface_Impl.sayGoodBye().equals("Good bye_hello_world"));
		Assert.assertTrue(simple_MyServiceInterface_Impl.sayHello().equals("Hello_hello_world"));
		Assert.assertTrue(simple_MyServiceInterface_Impl.sayHello("Geek").equals("Geek_hello_world"));
	}
	
}
