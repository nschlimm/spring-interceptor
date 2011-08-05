package com.schlimm.springcdi.interceptor.processor;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.target.SimpleBeanTargetSource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.schlimm.springcdi.interceptor.model.InterceptorMetaDataBean;
import com.schlimm.springcdi.interceptor.simple_methodlevel.Simple_MyServiceInterface_Impl2;
import com.schlimm.springcdi.interceptor.strategies.impl.SimpleInterceptorResolutionStrategy;


@ContextConfiguration("/test-context-interceptor-simple_methodlevel.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class InterceptedBeanInterceptorTest {

	@Autowired
	private BeanFactory beanFactory;
	
	private Simple_MyServiceInterface_Impl2 targetBean;
	
	private String beanName;
	
	private Simple_MyServiceInterface_Impl2 proxiedBean;
	
	private InterceptedBeanInterceptor interceptor;
	
	@Before
	public void setUp() {
		beanName = "simple_MyServiceInterface_Impl2";
		targetBean = (Simple_MyServiceInterface_Impl2) beanFactory.getBean(beanName);
		proxiedBean = (Simple_MyServiceInterface_Impl2) createProxyForBean(beanName, targetBean);
	}

	@SuppressWarnings("serial")
	private Object createProxyForBean(final String beanName, final Simple_MyServiceInterface_Impl2 bean) {
		final SimpleBeanTargetSource targetSource = new SimpleBeanTargetSource() {{setTargetBeanName(beanName); setTargetClass(bean.getClass()); setBeanFactory(beanFactory);}};
		ProxyFactory pf = new ProxyFactory() {{setTargetSource(targetSource); setProxyTargetClass(true);}};
		interceptor = new InterceptedBeanInterceptor(beanFactory, new InterceptorMetaDataBean(new SimpleInterceptorResolutionStrategy().resolveRegisteredInterceptors(beanFactory)),bean,beanName);
		pf.addAdvice(interceptor); pf.addInterface(InterceptorProxyInspector.class);
		Object proxy = pf.getProxy();
		return proxy;
	}
	
	@Test
	public void testInvoke() {
		Assert.assertTrue(proxiedBean.sayHello().equals("Hello_nonsense"));
	}
}
