package com.schlimm.springcdi;

/**
 * Interface implemented by Spring-CDI plugins. 
 * 
 * @author Niklas Schlimm
 *
 */
public interface SpringCDIPlugin {
	
	boolean executeLogic(Object... arguments);
	
}
