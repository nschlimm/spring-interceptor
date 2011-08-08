package com.schlimm.springcdi.interceptor.ct._91_C1.test1;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.schlimm.springcdi.interceptor.ct._91_C1.bindingtypes.DataAccess;

@Component
@Qualifier("impl2")
@DataAccess // must apply transaction and data access interceptor
public class CT91_TrialService_Impl2 implements CT91_TrialService_Interface {

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
