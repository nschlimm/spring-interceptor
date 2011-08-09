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

import com.schlimm.springcdi.interceptor.InterceptorModuleUtils;
import com.schlimm.springcdi.interceptor.model.InterceptorInfo;
import com.schlimm.springcdi.interceptor.model.MethodInterceptorInfo;


@ContextConfiguration("/test-context-interceptor-ct-91-C1.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class MethodLevelBindingsVisitorTest_CT_91 {

	@Autowired
	private ConfigurableListableBeanFactory beanFactory;

	private InterceptorInfo dataAccessInterceptor;

	private InterceptorInfo transactionInterceptor;

	private InterceptorInfoVisitor visitor = new MethodLevelBindingsVisitor();

	private BeanDefinition impl4Definition;
	
	@Before
	public void setUp() {
		dataAccessInterceptor = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("dataAccessInterceptor"), "dataAccessInterceptor"));
		transactionInterceptor = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("transactionInterceptor"), "transactionInterceptor"));
		impl4Definition = beanFactory.getBeanDefinition("CT91_TrialService_Impl4");
	}

	@Test
	public void testVisit_ExpectTransactionalAndDataAccess() {
		Assert.assertTrue(!transactionInterceptor.isInterceptingBean("CT91_TrialService_Impl4"));
		visitor.visit(transactionInterceptor, new BeanDefinitionHolder(impl4Definition, "CT91_TrialService_Impl4"));
		visitor.visit(dataAccessInterceptor, new BeanDefinitionHolder(impl4Definition, "CT91_TrialService_Impl4"));
		Assert.assertTrue(transactionInterceptor.isInterceptingBean("CT91_TrialService_Impl4"));
		Assert.assertTrue(dataAccessInterceptor.isInterceptingBean("CT91_TrialService_Impl4"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testVisit_ExpectDataAccessOnSayHelloWithArgs() throws Exception {
		Assert.assertTrue(!transactionInterceptor.isInterceptingBean("CT91_TrialService_Impl4"));
		visitor.visit(transactionInterceptor, new BeanDefinitionHolder(impl4Definition, "CT91_TrialService_Impl4"));
		visitor.visit(dataAccessInterceptor, new BeanDefinitionHolder(impl4Definition, "CT91_TrialService_Impl4"));
		Assert.assertTrue(transactionInterceptor.matches("CT91_TrialService_Impl4", InterceptorModuleUtils.getClass_forName(impl4Definition.getBeanClassName()).getMethod("sayHello", new Class[]{String.class})));
		Assert.assertTrue(dataAccessInterceptor.matches("CT91_TrialService_Impl4", InterceptorModuleUtils.getClass_forName(impl4Definition.getBeanClassName()).getMethod("sayHello", new Class[]{String.class})));
	}
	
}
