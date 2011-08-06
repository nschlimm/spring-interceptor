package com.schlimm.springcdi.interceptor.processor;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.lang.reflect.Method;

import junit.framework.Assert;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.schlimm.springcdi.interceptor.model.InterceptorMetaDataBean;
import com.schlimm.springcdi.interceptor.simple.Simple_MyServiceInterface_Impl;
import com.schlimm.springcdi.interceptor.strategies.impl.SimpleInterceptorResolutionStrategy;

@ContextConfiguration("/test-context-interceptor-simple_methodlevel.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class InterceptedBeanInterceptorTest_ClassLevelBindings {

	@Autowired
	private BeanFactory beanFactory;

	private String classLevelBindingsBeanName;
	
	private Simple_MyServiceInterface_Impl classLevelBindingsBean;
	
	private Method sayHelloWithoutArgs;

	private Method sayHelloWithArgs;
	
	private Method sayGoodBye;	
	
	private InterceptedBeanInterceptor interceptor;


	@Before
	public void setUp() throws Exception {
		classLevelBindingsBeanName = "simple_MyServiceInterface_Impl";
		classLevelBindingsBean = (Simple_MyServiceInterface_Impl) beanFactory.getBean(classLevelBindingsBeanName);
		interceptor = new InterceptedBeanInterceptor(beanFactory, new InterceptorMetaDataBean(new SimpleInterceptorResolutionStrategy().resolveRegisteredInterceptors(beanFactory)), classLevelBindingsBean,
				classLevelBindingsBeanName);
		sayHelloWithoutArgs = Simple_MyServiceInterface_Impl.class.getMethod("sayHello", new Class[] {});
		sayHelloWithArgs = Simple_MyServiceInterface_Impl.class.getMethod("sayHello", new Class[] {String.class});
		sayGoodBye = Simple_MyServiceInterface_Impl.class.getMethod("sayGoodBye", new Class[] {});
	}

	@Test
	public void testInvoke_sayHelloWithoutArgs() throws Throwable {
		MethodInvocation invocation = createMock(MethodInvocation.class);
		expect(invocation.getMethod()).andReturn(sayHelloWithoutArgs).anyTimes();
		expect(invocation.getArguments()).andReturn(new Object[]{});
		replay(invocation);
		Assert.assertTrue(interceptor.invoke(invocation).equals("Hello_nonsense"));
		verify(invocation);
	}
	
	@Test
	public void testInvoke_sayHelloWithArgs() throws Throwable {
		MethodInvocation invocation = createMock(MethodInvocation.class);
		expect(invocation.getMethod()).andReturn(sayHelloWithArgs).anyTimes();
		expect(invocation.getArguments()).andReturn(new Object[]{"Geek"});
		replay(invocation);
		Assert.assertTrue(interceptor.invoke(invocation).equals("Geek_nonsense"));
		verify(invocation);
	}
	
	@Test
	public void testInvoke_sayGoodBye() throws Throwable {
		MethodInvocation invocation = createMock(MethodInvocation.class);
		expect(invocation.getMethod()).andReturn(sayGoodBye).anyTimes();
		expect(invocation.getArguments()).andReturn(new Object[]{});
		replay(invocation);
		Assert.assertTrue(interceptor.invoke(invocation).equals("Good bye_nonsense"));
		verify(invocation);
	}
}
