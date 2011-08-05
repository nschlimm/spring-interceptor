package com.schlimm.springcdi.interceptor.processor;

import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;

import com.schlimm.springcdi.interceptor.model.InterceptorInfo;
import com.schlimm.springcdi.interceptor.model.InterceptorMetaDataBean;

public class InterceptedBeanInterceptor implements MethodInterceptor {

	private BeanFactory beanFactory;
	
	private InterceptorMetaDataBean metaDataBean;
	
	private Object targetBean;
	
	private String beanName;
	
	public InterceptedBeanInterceptor(BeanFactory beanFactory, InterceptorMetaDataBean metaDataBean, Object targetBean, String beanName) {
		super();
		this.beanFactory = beanFactory;
		this.metaDataBean = metaDataBean;
		this.targetBean = targetBean;
		this.beanName = beanName;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		List<InterceptorInfo> interceptors = metaDataBean.getMatchingInterceptors(beanName, invocation.getMethod());
		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(targetBean);
		for (InterceptorInfo interceptorInfo : interceptors) {
			Object interceptor = beanFactory.getBean(interceptorInfo.getBeanDefinitionHolder().getBeanName());
			for (Method method : interceptorInfo.getInterceptorMethods()) {
				JSR318InterceptorAdapter adapter = new JSR318InterceptorAdapter(method, interceptor);
				pf.addAdvice(adapter);
			}
		}
		Object proxy = pf.getProxy();
		return AopUtils.invokeJoinpointUsingReflection(proxy, invocation.getMethod(), invocation.getArguments());
	}

}
