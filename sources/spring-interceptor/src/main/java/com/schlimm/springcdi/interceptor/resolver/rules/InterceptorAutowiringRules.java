package com.schlimm.springcdi.interceptor.resolver.rules;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;

import com.schlimm.springcdi.SpringCDIPlugin;

/**
 * Interface that is implemented by interceptor autowiring rule sets.
 * 
 * Clients can implement their own rule sets to enhance wiring logic.
 * 
 * @author Niklas Schlimm
 *
 */
public interface InterceptorAutowiringRules extends SpringCDIPlugin {

	boolean applyInterceptorAutowiringRules(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor);

}
