package com.schlimm.springcdi.interceptor.simple;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("impl1")
@HelloWorldExtendible
public class Simple_MyServiceInterface_Impl implements Simple_MyServiceInterface {

	@Override
	public String sayHello() {
		return "Hello";
	}

	@Override
	public String sayHello(String what) {
		return what;
	}

	@Override
	public String sayGoodBye() {
		return "Good bye";
	}

}
