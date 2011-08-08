package com.schlimm.springcdi.interceptor;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import com.schlimm.springcdi.interceptor.model.InterceptorInfo;
import com.schlimm.springcdi.interceptor.model.InterceptorMetaDataBean;
import com.schlimm.springcdi.interceptor.processor.InterceptorAwareBeanPostProcessor;
import com.schlimm.springcdi.interceptor.strategies.InterceptorResolutionStrategy;
import com.schlimm.springcdi.interceptor.strategies.impl.SimpleInterceptorResolutionStrategy;

public class InterceptorAwareBeanFactoryPostProcessor implements BeanFactoryPostProcessor, InitializingBean {

	private static final String INTERCEPTOR_META_DATA_BEAN = "interceptorMetaDataBean";

	private InterceptorResolutionStrategy interceptorResolutionStrategy;

	public InterceptorAwareBeanFactoryPostProcessor() {
		super();
	}

	public InterceptorAwareBeanFactoryPostProcessor(InterceptorResolutionStrategy interceptorResolutionStrategy) {
		super();
		this.interceptorResolutionStrategy = interceptorResolutionStrategy;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		((DefaultListableBeanFactory) beanFactory).registerBeanDefinition("_interceptorPostProcessor", BeanDefinitionBuilder.rootBeanDefinition(InterceptorAwareBeanPostProcessor.class)
				.getBeanDefinition());
		if (beanFactory.getBeanNamesForType(InterceptorAwareBeanPostProcessor.class) == null) {
			throw new InterceptorAwareBeanFactoryPostProcessorException("Spring-CDI interceptor module requires DecoratorAwareBeanPostProcessor registered!");
		}
		createAndRegisterMetaDataBean(beanFactory);
	}

	public InterceptorMetaDataBean createAndRegisterMetaDataBean(ConfigurableListableBeanFactory beanFactory) {
		List<InterceptorInfo> interceptors = interceptorResolutionStrategy.resolveRegisteredInterceptors(beanFactory);
		InterceptorMetaDataBean metaDataBean = new InterceptorMetaDataBean(interceptors);
		beanFactory.registerSingleton(INTERCEPTOR_META_DATA_BEAN, metaDataBean);
		return metaDataBean;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (interceptorResolutionStrategy == null) {
			interceptorResolutionStrategy = new SimpleInterceptorResolutionStrategy();
		}
	}

}
