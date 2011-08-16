package com.schlimm.springcdi.interceptor.aopbeans.cglib;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.schlimm.springcdi.interceptor.ct._91_C1.test1.CT91_TrialService_Interface;

@ContextConfiguration("/test-context-interceptor-aopbeans.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class SimpleInterceptorTestCase {

	@Autowired @Qualifier("impl1")
	private CT91_TrialService_Interface someInterceptedBean1;
	
	@Autowired @Qualifier("impl2")
	private CT91_TrialService_Interface someInterceptedBean2;
	
	@Autowired @Qualifier("impl3")
	private CT91_TrialService_Interface someInterceptedBean3;
	
	@Test
	public void testHelloWorldExtension_OrdinaryAnnotatedBean() {
		Assert.assertTrue(someInterceptedBean3.sayHello().equals("Hello_transactioninterceptor_"));
	}
	
	@Test
	public void testHelloWorldExtension_StereotypedBean() {
		Assert.assertTrue(someInterceptedBean1.sayHello().contains("_transactioninterceptor_"));
	}
	
	@Test
	public void testHelloWorldExtension_BeanWithDataAccessBinding() {
		Assert.assertTrue(someInterceptedBean2.sayHello().contains("_transactioninterceptor_"));
		Assert.assertTrue(someInterceptedBean2.sayHello().contains("_dataaccessinterceptor_"));
	}
	
}
