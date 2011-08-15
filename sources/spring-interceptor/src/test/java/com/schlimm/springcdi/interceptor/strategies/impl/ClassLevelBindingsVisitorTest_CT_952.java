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

import com.schlimm.springcdi.interceptor.model.InterceptorInfo;
import com.schlimm.springcdi.interceptor.model.MethodInterceptorInfo;

@ContextConfiguration("/test-context-interceptor-ct-952-C1.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class ClassLevelBindingsVisitorTest_CT_952 {

	@Autowired
	private ConfigurableListableBeanFactory beanFactory;

	private InterceptorInfo managementComptetence;

	private InterceptorInfo clerkCompetencer;

	private BeanDefinition impl1Definition;
	
//	private BeanDefinition impl2Definition;

	private ClassLevelBindingsVisitor visitor = new ClassLevelBindingsVisitor();
	
	@Before
	public void setUp() {
		clerkCompetencer = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("clerkCompetenceInterceptor"), "clerkCompetenceInterceptor"));
		managementComptetence = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("managementCompetenceInterceptor"),
				"managementCompetenceInterceptor"));
		impl1Definition = beanFactory.getBeanDefinition("CT952_TrialServiceImpl1");
//		impl2Definition = beanFactory.getBeanDefinition("CT952_TrialServiceImpl2");
	}

	@Test
	public void testVisit_Impl1_ManagementCompetenceApplied() {
		visitor.visit(managementComptetence, new BeanDefinitionHolder(impl1Definition, "CT952_TrialServiceImpl1"));
		visitor.visit(clerkCompetencer, new BeanDefinitionHolder(impl1Definition, "CT952_TrialServiceImpl1"));
		Assert.assertTrue(managementComptetence.isInterceptingBean("CT952_TrialServiceImpl1"));
		Assert.assertTrue(!clerkCompetencer.isInterceptingBean("CT952_TrialServiceImpl1"));
	}

	@Test
	public void testVisit_Impl1_OneClassLevelInterception() {
		visitor.visit(managementComptetence, new BeanDefinitionHolder(impl1Definition, "CT952_TrialServiceImpl1"));
		Assert.assertTrue(managementComptetence.getClassLevelInterceptions().size()==1);
	}
	
}
