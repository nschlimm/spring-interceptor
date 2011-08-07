package com.schlimm.springcdi.interceptor.processor;

import java.lang.reflect.Method;
import java.util.Map;

import javax.interceptor.InvocationContext;

import org.aopalliance.intercept.MethodInvocation;

public class MethodInvocationWrapper implements InvocationContext {

	private MethodInvocation methodInvocation;
	
	public MethodInvocationWrapper(MethodInvocation methodInvocation) {
		super();
		this.methodInvocation = methodInvocation;
	}

	@Override
	public Map<String, Object> getContextData() {
		return InterceptedBeanProxyAdvice.getCurrentContextData();
	}
	
	public void addAllContextData(Map<String, Object> contextData) {
		this.getContextData().putAll(contextData);
	}
	
	public void addContextData(String name, Object value) {
		getContextData().put(name, value);
	}
	
	@Override
	public Method getMethod() {
		return methodInvocation.getMethod();
	}

	@Override
	public Object[] getParameters() {
		return methodInvocation.getArguments();
	}

	@Override
	public Object getTarget() {
		return methodInvocation.getThis();
	}

	@Override
	public Object getTimer() {
		// NOP
		return null;
	}

	@Override
	public Object proceed() throws Exception {
		Object retVal = null;
		try {
			retVal = methodInvocation.proceed();
		} catch (Throwable e) {
			throw new Exception(e);
		}
		return retVal;
	}

	@Override
	public void setParameters(Object[] params) {
		// NOP
	}

}
