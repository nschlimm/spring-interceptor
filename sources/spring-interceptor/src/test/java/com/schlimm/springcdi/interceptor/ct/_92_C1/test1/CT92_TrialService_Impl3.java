package com.schlimm.springcdi.interceptor.ct._92_C1.test1;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.schlimm.springcdi.interceptor.ct._91_C1.bindingtypes.Transactional;
import com.schlimm.springcdi.interceptor.ct._92_C1.bindingtypes.Secured;

@Component
@Qualifier("impl3")
public class CT92_TrialService_Impl3 implements CT92_TrialService_Interface {

	@Secured @Transactional //must apply all security interceptors and transactional
	@Override
	public String sayHello() {
		return "Hello";
	}

	@Secured // apply old and VIP security interceptor
	@Override
	public String sayHello(String what) {
		return what;
	}

	@Override
	public String sayGoodBye() {
		return "Good bye";
	}

}
