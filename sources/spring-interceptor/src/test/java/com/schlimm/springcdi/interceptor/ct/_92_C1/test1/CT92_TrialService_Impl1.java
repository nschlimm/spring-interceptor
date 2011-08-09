package com.schlimm.springcdi.interceptor.ct._92_C1.test1;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.schlimm.springcdi.interceptor.ct._92_C1.bindingtypes.Secured;

@Component
@Qualifier("impl1")
@Secured // must apply VIP, Old Security Interceptor
public class CT92_TrialService_Impl1 implements CT92_TrialService_Interface {

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
