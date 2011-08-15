package com.schlimm.springcdi.interceptor.ct._94_C1.test1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.schlimm.springcdi.interceptor.ct._92_C1.test1.CT92_TrialService_Interface;

@ContextConfiguration("/test-context-interceptor-ct-94-C1.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class SimpleInterceptorTestCase {

	@Autowired @Qualifier("impl4")
	private CT92_TrialService_Interface someInterceptedBean;
	
	/**
	 * Only old and vip interceptor are applied cause only @RuleSecured is declared
	 */
	@Test
	public void testSecuredAnnotation_ExpectingOldAndVIP_onSayHello() {
		Assert.assertTrue(someInterceptedBean.sayHello().contains("Hello_securityinterceptor__vipinterceptor__oldinterceptor__transactioninterceptor_"));
	}
	
}
