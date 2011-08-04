package com.schlimm.springcdi.interceptor.processor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.interceptor.InvocationContext;

public class StandardInvocationContext implements InvocationContext{

	private Object successingInterceptor;
	
	private Object target;
	
	private Method method;
	
	private Object[] parameters;
	
	private Map<String, Object> contextData = new HashMap<String, Object>();
	
	public StandardInvocationContext(Object target, Method method, Object[] parameters, Map<String, Object> contextData) {
		super();
		this.target = target;
		this.method = method;
		this.parameters = parameters;
		this.contextData = contextData;
	}

	@Override
	public Map<String, Object> getContextData() {
		return contextData;
	}

	@Override
	public Method getMethod() {
		return method;
	}

	@Override
	public Object[] getParameters() {
		return parameters;
	}

	@Override
	public Object getTarget() {
		return target;
	}

	@Override
	public Object getTimer() {
		// NOP
		return null;
	}

	@Override
	public Object proceed() throws Exception {
		// TODO check if target in interceptor, if yes delegate to invoke, otherwise delegate to target method
		// create invocation context if proceed with interceptor
		return null;
	}

	@Override
	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public void setContextData(Map<String, Object> contextData) {
		this.contextData = contextData;
	}

	public void setSuccessingInterceptor(Object successingInterceptor) {
		this.successingInterceptor = successingInterceptor;
	}

	public Object getSuccessingInterceptor() {
		return successingInterceptor;
	}

}
