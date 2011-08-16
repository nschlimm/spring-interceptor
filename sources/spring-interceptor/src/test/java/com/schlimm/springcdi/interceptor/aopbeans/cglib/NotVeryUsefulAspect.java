package com.schlimm.springcdi.interceptor.aopbeans.cglib;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class NotVeryUsefulAspect {

	@Pointcut("execution(* com.schlimm.springcdi.interceptor.ct._91_C1.test1.CT91_TrialService_Interface.*(..))")
	public void myDummyPointCut() {}
	
	@Before("myDummyPointCut()")
	public void sayHello() {
//		System.out.println("Aspect Hello");
	}
}
