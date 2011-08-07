package com.schlimm.springcdi.interceptor.resolver.rules;

import java.util.List;

import javax.decorator.Decorator;

import net.sf.cglib.proxy.Proxy;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import com.schlimm.springcdi.interceptor.model.InterceptorMetaDataBean;

/**
 * Class implements the wiring rules for autowiring CDI interceptors.
 * 
 * @author Niklas Schlimm
 * 
 */
@SuppressWarnings("unused")
public class BeanPostProcessorCDIAutowiringRules implements InterceptorAutowiringRules {

	private InterceptorMetaDataBean metaData;
	
	public BeanPostProcessorCDIAutowiringRules() {
		super();
	}

	@Override
	public boolean executeLogic(Object... arguments) {
		Assert.isTrue(arguments.length == 2, "Expect two arguments!");
		Assert.isTrue(arguments[0] instanceof BeanDefinitionHolder);
		Assert.isTrue(arguments[1] instanceof DependencyDescriptor);
		return applyInterceptorAutowiringRules((BeanDefinitionHolder) arguments[0], (DependencyDescriptor) arguments[1]);
	}

	@Override
	public boolean applyInterceptorAutowiringRules(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor) {
		return true;
	}
}
