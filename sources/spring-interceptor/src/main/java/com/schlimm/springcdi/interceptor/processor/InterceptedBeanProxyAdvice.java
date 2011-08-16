package com.schlimm.springcdi.interceptor.processor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.interceptor.InterceptorBinding;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.ReflectionUtils;

import com.schlimm.springcdi.interceptor.InterceptorAwareBeanFactoryPostProcessorException;
import com.schlimm.springcdi.interceptor.model.InterceptorInfo;
import com.schlimm.springcdi.interceptor.model.InterceptorMetaDataBean;

/**
 * {@link MethodInterceptor} that is applied to any intercepted target bean. If a method call to the target bean is intercepted by
 * user defined JSR 318 interceptors this advice creates a proxy for each target bean method that applies all the user defined JSR
 * 318 interceptors. The method proxies are cached to maximize performance.
 * 
 * Example: Bean ServiceImpl has a method called "sayHello()". The ServiceImpl has an {@link InterceptorBinding} declared that
 * points to MyJSR318Interceptor. The the logic is as folows: The {@link InterceptedBeanProxyAdvice} is applied to ServiceImpl.
 * If the sayHello() method is called the {@link InterceptedBeanProxyAdvice} creates a "method proxy", its target is ServiceImpl. 
 * The methid proxy applies the MyJSR318Interceptor advice. This logic is required 'cause every method on ServiceImple bean
 * can have different interceptor chains, thus will have different method proxies that apply these chains.
 * 
 * @author Niklas Schlimm
 * 
 */
public class InterceptedBeanProxyAdvice implements MethodInterceptor {

	/**
	 * Bean factory that holds all interceptors
	 */
	private BeanFactory beanFactory;

	/**
	 * Meta data for all registered interceptors
	 */
	private InterceptorMetaDataBean metaDataBean;

	/**
	 * The intercepted bean
	 */
	private Object targetBean;

	/**
	 * The name of the intercepted bean in the {@link BeanFactory}
	 */
	private String beanName;

	/**
	 * Thread local context data variable to share context data between JSR 318 interceptors
	 */
	private static ThreadLocal<Map<String, Object>> currentContextData = new ThreadLocal<Map<String, Object>>();

	/**
	 * The proxy cache for all method proxies
	 */
	private static Map<Method, Object> proxyCache = new HashMap<Method, Object>();

	private Object mutex = new Object();

	private Method getInterceptor_Method = null;

	public InterceptedBeanProxyAdvice(BeanFactory beanFactory, InterceptorMetaDataBean metaDataBean, Object targetBean, String beanName) {
		super();
		this.beanFactory = beanFactory;
		this.metaDataBean = metaDataBean;
		this.targetBean = targetBean;
		this.beanName = beanName;
		try {
			getInterceptor_Method = InterceptedBeanProxyAdviceInspector.class.getMethod("getInterceptor", new Class[] {});
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

	/**
	 * Get or create method proxy for the current {@link MethodInvocation} to the intercepted bean.
	 * 
	 * @param invocation
	 *            current invocation
	 * 
	 * @return method proxy that has all user defined interceptors applied
	 */
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
