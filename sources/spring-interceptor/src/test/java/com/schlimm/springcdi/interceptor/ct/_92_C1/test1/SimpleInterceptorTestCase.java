package com.schlimm.springcdi.interceptor.ct._92_C1.test1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("/test-context-interceptor-ct-92-C1.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class SimpleInterceptorTestCase {

	@Autowired @Qualifier("impl1")
	private CT92_TrialService_Interface someInterceptedBean1;
	
	@Test
	public void testSecuredAnnotation_ExpectingOldAndVIP() {
		Assert.assertTrue(someInterceptedBean1.sayHello().contains("_oldinterceptor_"));
		Assert.assertTrue(someInterceptedBean1.sayHello().contains("_vipinterceptor_"));
		Assert.assertTrue(!someInterceptedBean1.sayHello().contains("_securityinterceptor_"));
	}
	
}
