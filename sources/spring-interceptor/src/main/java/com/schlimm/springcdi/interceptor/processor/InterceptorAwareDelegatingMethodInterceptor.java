package com.schlimm.springcdi.interceptor.processor;

import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.schlimm.springcdi.interceptor.InterceptorAwareBeanFactoryPostProcessorException;
import com.schlimm.springcdi.interceptor.model.InterceptorInfo;
import com.schlimm.springcdi.interceptor.model.InterceptorMetaDataBean;

/**
 * {@link MethodInterceptor} that delegates all method calls to the invocation contexts.
 * 
 * @author Niklas Schlimm
 * 
 */
public class InterceptorAwareDelegatingMethodInterceptor implements MethodInterceptor {

	private Object targetBean;
	
	private String beanName;

	private Method proxyInspectorGetInterceptorTarget = null;

	private InterceptorMetaDataBean metaData;
	
	public InterceptorAwareDelegatingMethodInterceptor(String beanName, Object targetBean, InterceptorMetaDataBean metaData) {
		super();
		this.targetBean = targetBean;
		this.metaData = metaData;
		this.beanName = beanName;
		try {
			proxyInspectorGetInterceptorTarget = InvocationContextInterceptorProxyInspector.class.getMethod("getInterceptorTarget", new Class[] {});
		} catch (Exception e) {
			throw new InterceptorAwareBeanFactoryPostProcessorException("Could not instantiate decorator proxy!", e);
		}
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object retVal = null;
		if (invocation.getMethod().equals(proxyInspectorGetInterceptorTarget)) {
			return targetBean;
		}
		List<InterceptorInfo> interceptors = metaData.getMatchingInterceptors(beanName, invocation.getMethod());
		// wire interceptors => invocation contexts
		// last interceptor has the invocated method as target
		// create invocation context
		// call invoke on first interceptor
		return retVal;
	}

}
