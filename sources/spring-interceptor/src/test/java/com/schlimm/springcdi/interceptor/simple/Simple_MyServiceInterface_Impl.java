package com.schlimm.springcdi.interceptor.simple;

import org.springframework.stereotype.Component;

@Component
@HelloWorldExtendible
public class Simple_MyServiceInterface_Impl implements Simple_MyServiceInterface {

	@Override
	public String sayHello() {
		return "Hello";
	}

}
