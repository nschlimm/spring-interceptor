package com.schlimm.springcdi.interceptor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration("/test-context-interceptor-simple_methodlevel.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class InterceptorAwareBeanFactoryPostProcessorTest {

	@Autowired
	private ConfigurableListableBeanFactory beanFactory;
	
	private InterceptorAwareBeanFactoryPostProcessor postProcessor;
	
	@Before
	public void setUp() throws Exception {
		postProcessor = new InterceptorAwareBeanFactoryPostProcessor();
		postProcessor.afterPropertiesSet();
	}
	
	@Test
	public void testCreatedMetaDataBean() {
		Assert.assertTrue(postProcessor.createAndRegisterMetaDataBean(beanFactory).getInterceptors().size()>0);
		Assert.assertTrue(beanFactory.containsBean("interceptorMetaDataBean"));
	}

}
