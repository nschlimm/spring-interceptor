package com.schlimm.springcdi.interceptor.strategies.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.schlimm.springcdi.interceptor.InterceptorAwareBeanFactoryPostProcessorException;
import com.schlimm.springcdi.interceptor.model.InterceptorInfo;
import com.schlimm.springcdi.interceptor.model.MethodInterceptorInfo;
import com.schlimm.springcdi.interceptor.strategies.InterceptorResolutionStrategy;

/**
 * Simple interceptor resolution strategy that assumes that interceptors are scanned on the class path using Spring's component
 * scan. Interceptor {@link BeanDefinition} must be of type {@link AnnotatedBeanDefinition}.
 * 
 * @author Niklas Schlimm
 * 
 */
public class SimpleInterceptorResolutionStrategy implements InterceptorResolutionStrategy {

	private static final String SCOPED_TARGET = "scopedTarget.";

	private ArrayList<InterceptorInfo> registeredInterceptorsCache;

	private List<InterceptorInfoVisitor> visitors = Arrays.asList(new ClassLevelBindingsVisitor(), new MethodLevelBindingsVisitor());

	@Override
	public List<InterceptorInfo> resolveRegisteredInterceptors(BeanFactory beanFactory) {
		if (!(beanFactory instanceof ConfigurableListableBeanFactory))
			throw new InterceptorAwareBeanFactoryPostProcessorException("Simple interceptor strategy only supports ConfigurableListableBeanFactory");
		ConfigurableListableBeanFactory configurableListableBeanFactory = (ConfigurableListableBeanFactory) beanFactory;
		List<InterceptorInfo> interceptors = new ArrayList<InterceptorInfo>();
		if (registeredInterceptorsCache == null) {
			interceptors.addAll(collectInterceptors(configurableListableBeanFactory));
		} else {
			interceptors = registeredInterceptorsCache;
		}
		return interceptors;
	}

	private List<InterceptorInfo> collectInterceptors(ConfigurableListableBeanFactory configurableListableBeanFactory) {
		List<InterceptorInfo> interceptors = new ArrayList<InterceptorInfo>();
		registeredInterceptorsCache = new ArrayList<InterceptorInfo>();
		String[] bdNames = configurableListableBeanFactory.getBeanDefinitionNames();
		for (String bdName : bdNames) {
			BeanDefinition bd = configurableListableBeanFactory.getBeanDefinition(bdName);
			if (bd instanceof AnnotatedBeanDefinition) {
				AnnotatedBeanDefinition abd = (AnnotatedBeanDefinition) bd;
				if (InterceptorInfo.isInterceptor(abd)) {
					if (bdName.startsWith(SCOPED_TARGET)) {
						bd = configurableListableBeanFactory.getBeanDefinition(bdName.replace(SCOPED_TARGET, ""));
					}
					InterceptorInfo interceptorInfo = new MethodInterceptorInfo(new BeanDefinitionHolder(bd, bdName.replace(SCOPED_TARGET, "")));
					resolveInterceptorTargets(configurableListableBeanFactory, interceptorInfo);
					interceptors.add(interceptorInfo);
				}
			}
		}
		return interceptors;
	}

	public InterceptorInfo resolveInterceptorTargets(BeanFactory beanFactory, InterceptorInfo interceptorInfo) {
		ConfigurableListableBeanFactory configurableListableBeanFactory = (ConfigurableListableBeanFactory) beanFactory;
		String[] bnNames = configurableListableBeanFactory.getBeanDefinitionNames();
		for (String bdName : bnNames) {
			BeanDefinition bd = configurableListableBeanFactory.getBeanDefinition(bdName);
			BeanDefinitionHolder defHolder = new BeanDefinitionHolder(bd, bdName);
			if (bd instanceof AnnotatedBeanDefinition && !InterceptorInfo.isInterceptor((AnnotatedBeanDefinition) bd)) {
				for (InterceptorInfoVisitor visitor : visitors) {
					visitor.visit(interceptorInfo, defHolder);
				}
			}
		}
		return interceptorInfo;
	}

}
