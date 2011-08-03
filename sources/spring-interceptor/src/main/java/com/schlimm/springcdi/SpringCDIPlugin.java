package com.schlimm.springcdi;

/**
 * Interface implemented by Spring-CDI plugins. Enables registry of rule set plugin with {@link InterceptorAwareAutowireCandidateResolver}.
 * 
 * @author Niklas Schlimm
 * @see {@link ResolverCDIAutowiringRules}, {@link InterceptorAwareAutowireCandidateResolver}
 *
 */
public interface SpringCDIPlugin {
	
	boolean executeLogic(Object... arguments);
	
}
