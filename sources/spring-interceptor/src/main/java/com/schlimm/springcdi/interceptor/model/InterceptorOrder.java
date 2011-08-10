package com.schlimm.springcdi.interceptor.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class InterceptorOrder {

	private List<Class> ordererInterceptorClasses = new ArrayList<Class>();

	public void setOrdererInterceptorClasses(List<Class> ordererInterceptorClasses) {
		this.ordererInterceptorClasses = ordererInterceptorClasses;
	}

	public List<Class> getOrdererInterceptorClasses() {
		return ordererInterceptorClasses;
	}
	
}
