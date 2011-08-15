package com.schlimm.springcdi.interceptor.ct._952_C1.test1;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("/test-context-interceptor-ct-952-C1.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class SimpleInterceptorTestCase {

	@Autowired @Qualifier("impl1")
	private CT952_TrialService_Interface someInterceptedBean1;
	
	/**
	 * Only old and vip interceptor are applied cause only @RuleSecured is declared
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	@Test
	public void testRuleSecuredAnnotation_appliesManagementCompetenceInterceptor() throws SecurityException, NoSuchMethodException {
		assertTrue(someInterceptedBean1.sayHello().equals("hello_managementcompetence_"));
	}
	
}
