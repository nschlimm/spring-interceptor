package com.schlimm.springcdi.interceptor.simple;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("/test-context-interceptor-simple.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class SimpleInterceptorTestCase {

	@Autowired
	private Simple_MyServiceInterface someInterceptedBean;
	
	@Test
	public void testHelloWorldExtension() {
		Assert.assertTrue(someInterceptedBean.sayHello().equals("Hello_nonsense"));
	}
	
}
