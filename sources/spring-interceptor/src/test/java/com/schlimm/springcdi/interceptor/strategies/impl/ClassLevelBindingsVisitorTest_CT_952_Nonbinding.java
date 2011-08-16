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
public class ClassLevelBindingsVisitorTest_CT_952_Nonbinding {

	@Autowired
	private ConfigurableListableBeanFactory beanFactory;

	private InterceptorInfo managementComptetence;

	private InterceptorInfo clerkCompetencer;
	
	private InterceptorInfo revisionInterceptor;

	private BeanDefinition impl3Definition;
	
	private ClassLevelBindingsVisitor visitor = new ClassLevelBindingsVisitor();
	
	@Before
	public void setUp() {
		clerkCompetencer = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("clerkCompetenceInterceptor"), "clerkCompetenceInterceptor"));
		managementComptetence = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("managementCompetenceInterceptor"),
				"managementCompetenceInterceptor"));
		revisionInterceptor = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("revisionCompetenceInterceptor"),
		"revisionCompetenceInterceptor"));
		impl3Definition = beanFactory.getBeanDefinition("CT952_TrialServiceImpl3");
	}

	@Test
	public void testVisit_Impl3_RevisionInterceptorApplied_despiteOfDifferentNonbindingMemberValues() {
		visitor.visit(managementComptetence, new BeanDefinitionHolder(impl3Definition, "CT952_TrialServiceImpl3"));
		visitor.visit(clerkCompetencer, new BeanDefinitionHolder(impl3Definition, "CT952_TrialServiceImpl3"));
		visitor.visit(revisionInterceptor, new BeanDefinitionHolder(impl3Definition, "CT952_TrialServiceImpl3"));
		Assert.assertTrue(!managementComptetence.isInterceptingBean("CT952_TrialServiceImpl3"));
		Assert.assertTrue(clerkCompetencer.isInterceptingBean("CT952_TrialServiceImpl3"));
		Assert.assertTrue(revisionInterceptor.isInterceptingBean("CT952_TrialServiceImpl3"));
	}

}
