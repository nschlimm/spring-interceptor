package com.schlimm.springcdi.interceptor.processor;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.target.SimpleBeanTargetSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.schlimm.springcdi.interceptor.model.InterceptorMetaDataBean;

/**
 * {@link BeanPostProcessor} that applies the JSR-299 interceptor pattern to the Spring beans.
 * 
 * If the processed bean is an intercepted bean, then this {@link BeanPostProcessor} returns a CGLIB proxy for that bean. Uses a
 * {@link InterceptedBeanProxyAdvice} to delegate calls to interceptors for a specific method call.
 * 
 * @author Niklas Schlimm
 * 
 */
public class InterceptorAwareBeanPostProcessor implements BeanPostProcessor {

	@Autowired
	private InterceptorMetaDataBean metaData;

	@Autowired
	private ConfigurableListableBeanFactory beanFactory = null;

	
	public InterceptorAwareBeanPostProcessor() {
		super();
	}

	public InterceptorAwareBeanPostProcessor(InterceptorMetaDataBean metaData, ConfigurableListableBeanFactory beanFactory) {
		super();
		this.metaData = metaData;
		this.beanFactory = beanFactory;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		if (metaData.isInterceptedBean(beanName)) {
			return createProxyForBean(beanName, bean);
		} else {
			return bean;
		}
		
	}

	@SuppressWarnings("serial")
	private Object createProxyForBean(final String beanName, final Object bean) {
		final SimpleBeanTargetSource targetSource = new SimpleBeanTargetSource() {{setTargetBeanName(beanName);setTargetClass(bean.getClass());setBeanFactory(beanFactory);}};
		ProxyFactory pf = new ProxyFactory() {{setTargetSource(targetSource);setProxyTargetClass(true);}};
		InterceptedBeanProxyAdvice interceptor = new InterceptedBeanProxyAdvice(beanFactory, metaData, bean, beanName);
		pf.addAdvice(interceptor);
		pf.addInterface(InterceptedBeanProxyAdviceInspector.class);
		Object proxy = pf.getProxy();
		return proxy;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
