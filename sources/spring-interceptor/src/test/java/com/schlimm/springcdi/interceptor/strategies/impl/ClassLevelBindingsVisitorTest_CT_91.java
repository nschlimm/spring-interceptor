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

@ContextConfiguration("/test-context-interceptor-ct-91-C1.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class ClassLevelBindingsVisitorTest_CT_91 {

	@Autowired
	private ConfigurableListableBeanFactory beanFactory;

	private InterceptorInfo dataAccessInterceptor;

	private InterceptorInfo transactionInterceptor;

	private InterceptorInfoVisitor visitor = new ClassLevelBindingsVisitor();

	private BeanDefinition impl1Definition;
	
	private BeanDefinition impl2Definition;
	
	private BeanDefinition impl3Definition;

	@Before
	public void setUp() {
		dataAccessInterceptor = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("dataAccessInterceptor"), "dataAccessInterceptor"));
		transactionInterceptor = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("transactionInterceptor"), "transactionInterceptor"));
		impl1Definition = beanFactory.getBeanDefinition("CT91_TrialService_Impl1");
		impl2Definition = beanFactory.getBeanDefinition("CT91_TrialService_Impl2");
		impl3Definition = beanFactory.getBeanDefinition("CT91_TrialService_Impl3");
	}

	@Test
	public void testVisit_StereotypeExpectTransactional() {
		Assert.assertTrue(!transactionInterceptor.isInterceptingBean("CT91_TrialService_Impl1"));
		visitor.visit(transactionInterceptor, new BeanDefinitionHolder(impl1Definition, "CT91_TrialService_Impl1"));
		visitor.visit(dataAccessInterceptor, new BeanDefinitionHolder(impl1Definition, "CT91_TrialService_Impl1"));
		Assert.assertTrue(transactionInterceptor.isInterceptingBean("CT91_TrialService_Impl1"));
		Assert.assertTrue(!dataAccessInterceptor.isInterceptingBean("CT91_TrialService_Impl1"));
	}
	
	@Test
	public void testVisit_DataAccessExpectTransactionalAndDataAccess() {
		Assert.assertTrue(!transactionInterceptor.isInterceptingBean("CT91_TrialService_Impl2"));
		visitor.visit(transactionInterceptor, new BeanDefinitionHolder(impl2Definition, "CT91_TrialService_Impl2"));
		visitor.visit(dataAccessInterceptor, new BeanDefinitionHolder(impl2Definition, "CT91_TrialService_Impl2"));
		Assert.assertTrue(transactionInterceptor.isInterceptingBean("CT91_TrialService_Impl2"));
		Assert.assertTrue(dataAccessInterceptor.isInterceptingBean("CT91_TrialService_Impl2"));
	}
	
	@Test
	public void testVisit_TransactionalExpectTransactional() {
		Assert.assertTrue(!transactionInterceptor.isInterceptingBean("CT91_TrialService_Impl2"));
		visitor.visit(transactionInterceptor, new BeanDefinitionHolder(impl3Definition, "CT91_TrialService_Impl3"));
		visitor.visit(dataAccessInterceptor, new BeanDefinitionHolder(impl3Definition, "CT91_TrialService_Impl3"));
		Assert.assertTrue(transactionInterceptor.isInterceptingBean("CT91_TrialService_Impl3"));
		Assert.assertTrue(!dataAccessInterceptor.isInterceptingBean("CT91_TrialService_Impl3"));
	}
}
