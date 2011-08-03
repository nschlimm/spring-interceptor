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

/**
 * Class implements the wiring rules for autowiring CDI decorators when {@link DecoratorAwareBeanFactoryPostProcessor}
 * mode is set to 'processor'.
 * 
 * @author Niklas Schlimm
 * 
 */
@SuppressWarnings("unused")
public class BeanPostProcessorCDIAutowiringRules implements InterceptorAutowiringRules {

	private AutowireCandidateResolver resolver;

	private ConfigurableListableBeanFactory beanFactory;

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
//		if (metaData.isKnowDecorator(bdHolder.getBeanName())) {
//			return false;
//		}
		return true;
	}
}
