package com.schlimm.springcdi.interceptor.processor;

import java.lang.reflect.Method;
import java.util.Map;

import javax.interceptor.InvocationContext;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.ReflectionUtils;

public class JSR318InterceptorMethodAdapter implements MethodInterceptor {

	private Method jsr318InterceptorMethod;

	private Object interceptor;
	
	private Map<String, Object> contextData;

	public JSR318InterceptorMethodAdapter(Method interceptorMethod, Object interceptor) {
		super();
		this.jsr318InterceptorMethod = interceptorMethod;
		this.interceptor = interceptor;
	}

	public JSR318InterceptorMethodAdapter(Method jsr318InterceptorMethod, Object interceptor, Map<String, Object> contextData) {
		super();
		this.jsr318InterceptorMethod = jsr318InterceptorMethod;
		this.interceptor = interceptor;
		this.contextData = contextData;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object retVal = null;
		InvocationContext context = new MethodInvocationWrapper(invocation, contextData);
		retVal = ReflectionUtils.invokeMethod(jsr318InterceptorMethod, interceptor, context);
		return retVal;
	}

}
