package com.schlimm.springcdi.interceptor.processor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.ReflectionUtils;

import com.schlimm.springcdi.interceptor.InterceptorAwareBeanFactoryPostProcessorException;
import com.schlimm.springcdi.interceptor.model.InterceptorInfo;
import com.schlimm.springcdi.interceptor.model.InterceptorMetaDataBean;

public class InterceptedBeanInterceptor implements MethodInterceptor {

	private BeanFactory beanFactory;
	
	private InterceptorMetaDataBean metaDataBean;
	
	private Object targetBean;
	
	private String beanName;
	
	private Method getInterceptor_Method = null;
	
	private Map<String, Object> currentContextData = new HashMap<String, Object>();
	
	public InterceptedBeanInterceptor(BeanFactory beanFactory, InterceptorMetaDataBean metaDataBean, Object targetBean, String beanName) {
		super();
		this.beanFactory = beanFactory;
		this.metaDataBean = metaDataBean;
		this.targetBean = targetBean;
		this.beanName = beanName;
		try {
			getInterceptor_Method = InterceptorProxyInspector.class.getMethod("getInterceptor", new Class[]{});
		} catch (Exception e) {
			throw new InterceptorAwareBeanFactoryPostProcessorException("Could not instintiate interceptor!", e);
		}
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (getInterceptor_Method.equals(invocation.getMethod()))
			return this;
		currentContextData.clear();
		List<InterceptorInfo> interceptors = getInterceptors(invocation);
		Object proxy = createProxyWithInterceptors(interceptors, currentContextData);
		Object retVal = performCall(invocation, proxy);
		return retVal;
	}

	public List<InterceptorInfo> getInterceptors(MethodInvocation invocation) {
		return metaDataBean.getMatchingInterceptors(beanName, invocation.getMethod());
	}

	public Object performCall(MethodInvocation invocation, Object proxy) {
		return ReflectionUtils.invokeMethod(invocation.getMethod(), proxy, invocation.getArguments());
	}

	public Object createProxyWithInterceptors(List<InterceptorInfo> interceptors, Map<String, Object> callContextData) {
		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(targetBean);
		for (InterceptorInfo interceptorInfo : interceptors) {
			Object interceptor = beanFactory.getBean(interceptorInfo.getBeanDefinitionHolder().getBeanName());
			for (Method method : interceptorInfo.getInterceptorMethods()) {
				JSR318InterceptorMethodAdapter adapter = new JSR318InterceptorMethodAdapter(method, interceptor, callContextData);
				pf.addAdvice(adapter);
			}
		}
		Object proxy = pf.getProxy();
		return proxy;
	}

	public Map<String, Object> getCurrentContextData() {
		return currentContextData;
	}

}
