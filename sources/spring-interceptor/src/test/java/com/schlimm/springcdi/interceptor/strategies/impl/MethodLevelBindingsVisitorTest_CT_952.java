package com.schlimm.springcdi.interceptor.strategies.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.schlimm.springcdi.interceptor.ct._952_C1.test1.CT952_TrialServiceImpl2;
import com.schlimm.springcdi.interceptor.model.InterceptorInfo;
import com.schlimm.springcdi.interceptor.model.MethodInterceptorInfo;


@ContextConfiguration("/test-context-interceptor-ct-952-C1.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class MethodLevelBindingsVisitorTest_CT_952 {

	@Autowired
	private ConfigurableListableBeanFactory beanFactory;

	private InterceptorInfo managementComptetence;

	private InterceptorInfo clerkCompetencer;

	private BeanDefinition impl2Definition;
	
	private MethodLevelBindingsVisitor visitor = new MethodLevelBindingsVisitor();
	
	@Before
	public void setUp() {
		clerkCompetencer = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("clerkCompetenceInterceptor"), "clerkCompetenceInterceptor"));
		managementComptetence = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("managementCompetenceInterceptor"),
				"managementCompetenceInterceptor"));
		impl2Definition = beanFactory.getBeanDefinition("CT952_TrialServiceImpl2");
	}

	@Test
	public void testVisit_Impl2_ClerkCompetenceApplied() {
		visitor.visit(managementComptetence, new BeanDefinitionHolder(impl2Definition, "CT952_TrialServiceImpl2"));
		Assert.assertTrue(!managementComptetence.isInterceptingBean("CT952_TrialServiceImpl2"));
		visitor.visit(clerkCompetencer, new BeanDefinitionHolder(impl2Definition, "CT952_TrialServiceImpl2"));
		Assert.assertTrue(clerkCompetencer.isInterceptingBean("CT952_TrialServiceImpl2"));
	}

	@Test
	public void testVisit_Impl2_OneMethodIntercepted() {
		visitor.visit(clerkCompetencer, new BeanDefinitionHolder(impl2Definition, "CT952_TrialServiceImpl2"));
		Assert.assertTrue(clerkCompetencer.getInterceptedMethods().size()==1);
	}
	
	@Test
	public void testVisit_Impl2_sayHelloIntercepted_byClerInterceptor() throws SecurityException, NoSuchMethodException {
		visitor.visit(clerkCompetencer, new BeanDefinitionHolder(impl2Definition, "CT952_TrialServiceImpl2"));
		Assert.assertTrue(clerkCompetencer.getInterceptedMethods().contains(CT952_TrialServiceImpl2.class.getMethod("sayHello", new Class[]{})));
	}
	
	@Test
	public void testVisit_Impl2_sayHelloNotIntercepted_byManagementInterceptor() throws SecurityException, NoSuchMethodException {
		visitor.visit(managementComptetence, new BeanDefinitionHolder(impl2Definition, "CT952_TrialServiceImpl2"));
		Assert.assertTrue(!managementComptetence.getInterceptedMethods().contains(CT952_TrialServiceImpl2.class.getMethod("sayHello", new Class[]{})));
	}
	
}
