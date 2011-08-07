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

public class InterceptedBeanProxyAdvice implements MethodInterceptor {

	private BeanFactory beanFactory;

	private InterceptorMetaDataBean metaDataBean;

	private Object targetBean;

	private String beanName;

	private Method getInterceptor_Method = null;

	private static ThreadLocal<Map<String, Object>> currentContextData = new ThreadLocal<Map<String, Object>>();

	private static Map<Method, Object> proxyCache = new HashMap<Method, Object>();

	private Object mutex = new Object();

	public InterceptedBeanProxyAdvice(BeanFactory beanFactory, InterceptorMetaDataBean metaDataBean, Object targetBean, String beanName) {
		super();
		this.beanFactory = beanFactory;
		this.metaDataBean = metaDataBean;
		this.targetBean = targetBean;
		this.beanName = beanName;
		try {
			getInterceptor_Method = InterceptorProxyInspector.class.getMethod("getInterceptor", new Class[] {});
		} catch (Exception e) {
			throw new InterceptorAwareBeanFactoryPostProcessorException("Could not instintiate interceptor!", e);
		}
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		if (getInterceptor_Method.equals(invocation.getMethod()))
			return this;
		currentContextData.set(new HashMap<String, Object>());
		Object proxy = getProxy(invocation);
		Object retVal = performCall(invocation, proxy);
		return retVal;
	}

	public Object getProxy(MethodInvocation invocation) {
		Object proxy;
		if (getProxyCache().containsKey(invocation.getMethod())) {
			proxy = getProxyCache().get(invocation.getMethod());
		} else {
			synchronized (mutex) {
				List<InterceptorInfo> interceptors = getInterceptors(invocation);
				proxy = createProxyWithInterceptors(interceptors);
				getProxyCache().put(invocation.getMethod(), proxy);
			}
		}
		return proxy;
	}

	public List<InterceptorInfo> getInterceptors(MethodInvocation invocation) {
		return metaDataBean.getMatchingInterceptors(beanName, invocation.getMethod());
	}

	public Object performCall(MethodInvocation invocation, Object proxy) {
		return ReflectionUtils.invokeMethod(invocation.getMethod(), proxy, invocation.getArguments());
	}

	public Object createProxyWithInterceptors(List<InterceptorInfo> interceptors) {
		ProxyFactory pf = new ProxyFactory();
		pf.setTarget(targetBean);
		for (InterceptorInfo interceptorInfo : interceptors) {
			Object interceptor = beanFactory.getBean(interceptorInfo.getBeanDefinitionHolder().getBeanName());
			for (Method method : interceptorInfo.getInterceptorMethods()) {
				InterceptorMethodAdapter adapter = new InterceptorMethodAdapter(method, interceptor);
				pf.addAdvice(adapter);
			}
		}
		Object proxy = pf.getProxy();
		return proxy;
	}

	public static Map<String, Object> getCurrentContextData() {
		return currentContextData.get();
	}

	public static Map<Method, Object> getProxyCache() {
		return proxyCache;
	}

	public static synchronized void resetProxyCache() {
		proxyCache.clear();
	}

}
