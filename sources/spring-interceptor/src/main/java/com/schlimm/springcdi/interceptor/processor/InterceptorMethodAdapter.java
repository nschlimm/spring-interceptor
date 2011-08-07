package com.schlimm.springcdi.interceptor.processor;

import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.ReflectionUtils;

public class InterceptorMethodAdapter implements MethodInterceptor {

	private Method jsr318InterceptorMethod;

	private Object interceptor;
	
	public InterceptorMethodAdapter(Method interceptorMethod, Object interceptor) {
		super();
		this.jsr318InterceptorMethod = interceptorMethod;
		this.interceptor = interceptor;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object retVal = null;
		InvocationContext context = new MethodInvocationWrapper(invocation);
		retVal = ReflectionUtils.invokeMethod(jsr318InterceptorMethod, interceptor, context);
		return retVal;
	}

}
