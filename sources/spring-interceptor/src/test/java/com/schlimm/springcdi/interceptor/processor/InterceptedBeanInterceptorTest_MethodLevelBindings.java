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
import com.schlimm.springcdi.interceptor.simple_methodlevel.Simple_MyServiceInterface_Impl2;
import com.schlimm.springcdi.interceptor.strategies.impl.SimpleInterceptorResolutionStrategy;

@ContextConfiguration("/test-context-interceptor-simple_methodlevel.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class InterceptedBeanInterceptorTest_MethodLevelBindings {

	@Autowired
	private BeanFactory beanFactory;

	private String methodLevelBindingsBeanName;
	
	private Simple_MyServiceInterface_Impl2 methodLevelBindingsBean;
	
	private Method sayHelloWithoutArgs;

	private Method sayHelloWithArgs;
	
	private Method sayGoodBye;	
	
	private InterceptedBeanInterceptor interceptor;


	@Before
	public void setUp() throws Exception {
		methodLevelBindingsBeanName = "simple_MyServiceInterface_Impl2";
		methodLevelBindingsBean = (Simple_MyServiceInterface_Impl2) beanFactory.getBean(methodLevelBindingsBeanName);
		interceptor = new InterceptedBeanInterceptor(beanFactory, new InterceptorMetaDataBean(new SimpleInterceptorResolutionStrategy().resolveRegisteredInterceptors(beanFactory)), methodLevelBindingsBean,
				methodLevelBindingsBeanName);
		sayHelloWithoutArgs = Simple_MyServiceInterface_Impl2.class.getMethod("sayHello", new Class[] {});
		sayHelloWithArgs = Simple_MyServiceInterface_Impl2.class.getMethod("sayHello", new Class[] {String.class});
		sayGoodBye = Simple_MyServiceInterface_Impl2.class.getMethod("sayGoodBye", new Class[] {});
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
		Assert.assertTrue(interceptor.invoke(invocation).equals("Good bye"));
		verify(invocation);
	}
	
	@Test
	public void testContextData() throws Throwable {
		MethodInvocation invocation = createMock(MethodInvocation.class);
		expect(invocation.getMethod()).andReturn(sayHelloWithoutArgs).anyTimes();
		expect(invocation.getArguments()).andReturn(new Object[]{});
		replay(invocation);
		interceptor.invoke(invocation);
		Assert.assertTrue(interceptor.getCurrentContextData().size()==1);
		verify(invocation);
	}
}
