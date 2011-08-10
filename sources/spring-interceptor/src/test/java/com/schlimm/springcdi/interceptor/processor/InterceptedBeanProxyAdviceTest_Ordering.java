package com.schlimm.springcdi.interceptor.processor;

import java.util.List;

import org.aopalliance.intercept.MethodInvocation;
import static org.easymock.EasyMock.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.schlimm.springcdi.interceptor.ct._91_C1.interceptors.TransactionInterceptor;
import com.schlimm.springcdi.interceptor.ct._92_C1.interceptors.OldSecurityInterceptor;
import com.schlimm.springcdi.interceptor.ct._92_C1.interceptors.SecurityInterceptor;
import com.schlimm.springcdi.interceptor.ct._92_C1.interceptors.VIPSecurityInterceptor;
import com.schlimm.springcdi.interceptor.ct._92_C1.test1.CT92_TrialService_Impl4;
import com.schlimm.springcdi.interceptor.ct._92_C1.test1.CT92_TrialService_Interface;
import com.schlimm.springcdi.interceptor.model.InterceptorInfo;
import com.schlimm.springcdi.interceptor.processor.InterceptedBeanProxyAdvice;
import com.schlimm.springcdi.interceptor.processor.InterceptedBeanProxyAdviceInspector;

@ContextConfiguration("/test-context-interceptor-ct-94-C1.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class InterceptedBeanProxyAdviceTest_Ordering {

	@Autowired @Qualifier("impl4")
	private CT92_TrialService_Interface someInterceptedBean1;
	
	/**
	 * Test order as supplied by InterceptedBeanProxyAdvice.getInterceptors
	 * @throws Exception
	 */
	@Test
	public void testOrder_getInterceptors_sayHello() throws Exception {
		InterceptedBeanProxyAdvice advice = ((InterceptedBeanProxyAdviceInspector) someInterceptedBean1).getInterceptor();
		MethodInvocation invocation = createMock(MethodInvocation.class);
		expect(invocation.getMethod()).andReturn(CT92_TrialService_Impl4.class.getMethod("sayHello", new Class[]{})).anyTimes();
		replay(invocation);
		List<InterceptorInfo> interceptors = advice.getInterceptors(invocation);
		Assert.assertTrue(interceptors.size()==4);
		Assert.assertTrue(interceptors.get(0).getBeanDefinitionHolder().getBeanDefinition().getBeanClassName().equals(TransactionInterceptor.class.getName()));
		Assert.assertTrue(interceptors.get(1).getBeanDefinitionHolder().getBeanDefinition().getBeanClassName().equals(OldSecurityInterceptor.class.getName()));
		Assert.assertTrue(interceptors.get(2).getBeanDefinitionHolder().getBeanDefinition().getBeanClassName().equals(VIPSecurityInterceptor.class.getName()));
		Assert.assertTrue(interceptors.get(3).getBeanDefinitionHolder().getBeanDefinition().getBeanClassName().equals(SecurityInterceptor.class.getName()));
	}
	
	/**
	 * Test order of advised interceptors
	 * @throws Exception
	 */
	@Test
	public void testOrder_advisedInterceptors_sayHello() throws Exception {
		InterceptedBeanProxyAdvice advice = ((InterceptedBeanProxyAdviceInspector) someInterceptedBean1).getInterceptor();
		MethodInvocation invocation = createMock(MethodInvocation.class);
		expect(invocation.getMethod()).andReturn(CT92_TrialService_Impl4.class.getMethod("sayHello", new Class[]{})).anyTimes();
		replay(invocation);
		List<InterceptorInfo> interceptors = advice.getInterceptors(invocation);
		CT92_TrialService_Impl4 impl4Proxy = (CT92_TrialService_Impl4) advice.createProxyWithInterceptors(interceptors);
		Advised proxy = (Advised) impl4Proxy;
		Assert.assertTrue(((InterceptorMethodAdapter)proxy.getAdvisors()[0].getAdvice()).getInterceptor().getClass().getName().equals(TransactionInterceptor.class.getName()));
		Assert.assertTrue(((InterceptorMethodAdapter)proxy.getAdvisors()[1].getAdvice()).getInterceptor().getClass().getName().equals(OldSecurityInterceptor.class.getName()));
		Assert.assertTrue(((InterceptorMethodAdapter)proxy.getAdvisors()[2].getAdvice()).getInterceptor().getClass().getName().equals(VIPSecurityInterceptor.class.getName()));
		Assert.assertTrue(((InterceptorMethodAdapter)proxy.getAdvisors()[3].getAdvice()).getInterceptor().getClass().getName().equals(SecurityInterceptor.class.getName()));
	}
	
}
