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
	
	@Autowired @Qualifier("impl2")
	private CT952_TrialService_Interface someInterceptedBean2;
	
	@Autowired @Qualifier("impl3")
	private CT952_TrialService_Interface someInterceptedBean3;
	
	@Test
	public void testRuleSecuredAnnotation_appliesManagementCompetenceInterceptor() throws SecurityException, NoSuchMethodException {
		assertTrue(someInterceptedBean1.sayHello().equals("hello_managementcompetence_"));
	}
	
	@Test
	public void testRuleSecuredAnnotation_MethodLevel_appliesClerkCompetenceInterceptor() throws SecurityException, NoSuchMethodException {
		assertTrue(someInterceptedBean2.sayHello().equals("hello_clerkcompetence_"));
	}
	
	@Test
	public void testRuleSecuredAnnotation_MethodLevel_sayHelloWithArgs_appliesMothing() throws SecurityException, NoSuchMethodException {
		assertTrue(someInterceptedBean2.sayHello("foo").equals("foo"));
	}
	
	@Test
	public void testRevisionEnabledAndRuleSecuredAnnotation_appliesRevisionAndClerkCompetenceInterceptor() throws SecurityException, NoSuchMethodException {
		assertTrue(someInterceptedBean3.sayHello().equals("hello_revisioncompetence__clerkcompetence_"));
	}
	
}
