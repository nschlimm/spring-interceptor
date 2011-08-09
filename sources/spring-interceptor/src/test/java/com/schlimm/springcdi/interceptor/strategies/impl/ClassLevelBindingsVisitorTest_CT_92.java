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

@ContextConfiguration("/test-context-interceptor-ct-92-C1.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class ClassLevelBindingsVisitorTest_CT_92 {

	@Autowired
	private ConfigurableListableBeanFactory beanFactory;

	private InterceptorInfo dataAccessInterceptor;

	private InterceptorInfo transactionInterceptor;

	private InterceptorInfo securityInterceptor;
	
	private InterceptorInfo oldSecurityInterceptor;
	
	private InterceptorInfo vipInterceptor;
	
	private InterceptorInfoVisitor visitor = new ClassLevelBindingsVisitor();

	private BeanDefinition impl1Definition;
	
	private BeanDefinition impl2Definition;
	
	@Before
	public void setUp() {
		dataAccessInterceptor = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("dataAccessInterceptor"), "dataAccessInterceptor"));
		transactionInterceptor = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("transactionInterceptor"), "transactionInterceptor"));
		securityInterceptor = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("securityInterceptor"), "securityInterceptor"));
		oldSecurityInterceptor = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("oldSecurityInterceptor"), "oldSecurityInterceptor"));
		vipInterceptor = new MethodInterceptorInfo(new BeanDefinitionHolder(((DefaultListableBeanFactory) beanFactory).getBeanDefinition("VIPSecurityInterceptor"), "VIPSecurityInterceptor"));
		impl1Definition = beanFactory.getBeanDefinition("CT92_TrialService_Impl1");
		impl2Definition = beanFactory.getBeanDefinition("CT92_TrialService_Impl2");
	}

	@Test
	public void testVisit_Impl1_OldSecurityAndVIPInterceptorsAreApplied() {
		visitor.visit(oldSecurityInterceptor, new BeanDefinitionHolder(impl1Definition, "CT92_TrialService_Impl1"));
		visitor.visit(vipInterceptor, new BeanDefinitionHolder(impl1Definition, "CT92_TrialService_Impl1"));
		Assert.assertTrue(oldSecurityInterceptor.isInterceptingBean("CT92_TrialService_Impl1"));
		Assert.assertTrue(vipInterceptor.isInterceptingBean("CT92_TrialService_Impl1"));
	}
	
	@Test
	public void testVisit_Impl1_NoDataAndTransactionInterceptor() {
		visitor.visit(transactionInterceptor, new BeanDefinitionHolder(impl1Definition, "CT92_TrialService_Impl1"));
		visitor.visit(dataAccessInterceptor, new BeanDefinitionHolder(impl1Definition, "CT92_TrialService_Impl1"));
		Assert.assertTrue(!transactionInterceptor.isInterceptingBean("CT92_TrialService_Impl1"));
		Assert.assertTrue(!dataAccessInterceptor.isInterceptingBean("CT92_TrialService_Impl1"));
	}
	
	@Test
	public void testVisit_Impl1_NoSecurtiyInterceptor() {
		visitor.visit(securityInterceptor, new BeanDefinitionHolder(impl1Definition, "CT92_TrialService_Impl1"));
		Assert.assertTrue(!securityInterceptor.isInterceptingBean("CT92_TrialService_Impl1"));
	}
	
	@Test
	public void testVisit_Impl2_OldSecurityAndVIPInterceptorsAreApplied() {
		visitor.visit(oldSecurityInterceptor, new BeanDefinitionHolder(impl2Definition, "CT92_TrialService_Impl2"));
		visitor.visit(vipInterceptor, new BeanDefinitionHolder(impl2Definition, "CT92_TrialService_Impl2"));
		Assert.assertTrue(oldSecurityInterceptor.isInterceptingBean("CT92_TrialService_Impl2"));
		Assert.assertTrue(vipInterceptor.isInterceptingBean("CT92_TrialService_Impl2"));
	}
	
	@Test
	public void testVisit_Impl2_TransactionInterceptorApplied() {
		visitor.visit(transactionInterceptor, new BeanDefinitionHolder(impl2Definition, "CT92_TrialService_Impl2"));
		Assert.assertTrue(transactionInterceptor.isInterceptingBean("CT92_TrialService_Impl2"));
	}
	
	@Test
	public void testVisit_Impl2_DataAccessInterceptorApplied() {
		visitor.visit(dataAccessInterceptor, new BeanDefinitionHolder(impl2Definition, "CT92_TrialService_Impl2"));
		Assert.assertTrue(!dataAccessInterceptor.isInterceptingBean("CT92_TrialService_Impl2"));
	}
	
	@Test
	public void testVisit_Impl2_SecurtiyInterceptorApplied() {
		visitor.visit(securityInterceptor, new BeanDefinitionHolder(impl2Definition, "CT92_TrialService_Impl2"));
		Assert.assertTrue(securityInterceptor.isInterceptingBean("CT92_TrialService_Impl2"));
	}
	
}
