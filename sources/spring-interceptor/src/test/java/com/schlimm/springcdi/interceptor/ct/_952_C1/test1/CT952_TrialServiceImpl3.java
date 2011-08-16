package com.schlimm.springcdi.interceptor.ct._952_C1.test1;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.schlimm.springcdi.interceptor.ct._952_C1.bindingtypes.RevisionEnabled;
import com.schlimm.springcdi.interceptor.ct._952_C1.bindingtypes.RuleSecured;

@Component
@Qualifier("impl3")
@RuleSecured(competenceLevel="clerk") @RevisionEnabled(competenceLevel="management", rivisor="rahn") // applies clerk and revision interceptor
public class CT952_TrialServiceImpl3 implements CT952_TrialService_Interface {

	@Override
	public String sayHello() {
		return "hello";
	}

	@Override
	public String sayHello(String what) {
		return what;
	}

	@Override
	public String sayGoodBye() {
		return "good bye";
	}

}
