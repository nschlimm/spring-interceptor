package com.schlimm.springcdi.interceptor.processor;

import java.lang.reflect.Method;
import java.util.HashMap;

import javax.interceptor.InvocationContext;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.ReflectionUtils;

public class JSR318InterceptorAdapter implements MethodInterceptor {

	private Method jsr318InterceptorMethod;

	private Object interceptor;

	public JSR318InterceptorAdapter(Method interceptorMethod, Object interceptor) {
		super();
		this.jsr318InterceptorMethod = interceptorMethod;
		this.interceptor = interceptor;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object retVal = null;
		InvocationContext context = new StandardInvocationContext(invocation.getThis(), invocation.getMethod(), invocation.getArguments(), new HashMap<String, Object>());
		retVal = ReflectionUtils.invokeMethod(jsr318InterceptorMethod, interceptor, context);
		return retVal;
	}

}
