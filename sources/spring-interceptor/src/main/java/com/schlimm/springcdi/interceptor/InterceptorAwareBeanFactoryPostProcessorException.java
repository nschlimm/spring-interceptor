package com.schlimm.springcdi.interceptor;

import org.springframework.core.NestedRuntimeException;

/**
 * Exception that is thrown by decorator pattern implementation logic.
 * 
 * @author Niklas Schlimm
 *
 */
public class InterceptorAwareBeanFactoryPostProcessorException extends NestedRuntimeException {

	/**
	 * Unique ID
	 */
	private static final long serialVersionUID = -2241526745257996175L;

	public InterceptorAwareBeanFactoryPostProcessorException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InterceptorAwareBeanFactoryPostProcessorException(String msg) {
		super(msg);
	}

	public InterceptorAwareBeanFactoryPostProcessorException(String msg, ClassNotFoundException e) {
		super(msg, e);
	}

}
