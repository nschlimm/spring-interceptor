package com.schlimm.springcdi.interceptor.ct._92_C1.test1;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.schlimm.springcdi.interceptor.ct._91_C1.bindingtypes.DataAccess;
import com.schlimm.springcdi.interceptor.ct._91_C1.bindingtypes.Transactional;
import com.schlimm.springcdi.interceptor.ct._92_C1.bindingtypes.Secured;

@Component
@Qualifier("impl4")
@Transactional
public class CT92_TrialService_Impl4 implements CT92_TrialService_Interface {

	@Override
	@Secured // must be all security interceptor and transaction interceptor applied
	public String sayHello() {
		return "Hello";
	}

	@Override
	@DataAccess // data access and transactional
	public String sayHello(String what) {
		return what;
	}

	@Override // only transactional
	public String sayGoodBye() {
		return "Good bye";
	}

}
