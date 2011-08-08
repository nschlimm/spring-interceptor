package com.schlimm.springcdi.interceptor.ct._91_C1.interceptors;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.schlimm.springcdi.interceptor.ct._91_C1.bindingtypes.DataAccess;

@Interceptor @DataAccess
public class DataAccessInterceptor {

	@AroundInvoke
	public Object manageTransaction(InvocationContext ctx) throws Exception {
		String result = null;
		ctx.getContextData().put(this.getClass().getName(), "");
		try {
			result = (String)ctx.proceed();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result + "_dataaccessinterceptor_";
	}

}
