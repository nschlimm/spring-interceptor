package com.schlimm.springcdi.interceptor.simple;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor @HelloWorldExtendible
public class Simple_MyInterceptor {

	@AroundInvoke
	public String extendReturnValueWithSomeNonsense(InvocationContext ctx) throws Exception {
		String result = null;
		ctx.getContextData().put("Some", "Nonsense");
		try {
			result = (String)ctx.proceed();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result + "_nonsense";
	}
	
}
