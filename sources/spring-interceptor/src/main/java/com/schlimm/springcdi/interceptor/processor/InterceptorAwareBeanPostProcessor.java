package com.schlimm.springcdi.interceptor.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.schlimm.springcdi.interceptor.model.InterceptorMetaDataBean;

/**
 * {@link BeanPostProcessor} that applies the JSR-299 decorator pattern to the Spring beans.
 * 
 * If the processed bean is a decorated bean, then this {@link BeanPostProcessor} returns a CGLIB proxy for that bean. Uses a
 * {@link InterceptorAwareDelegatingMethodInterceptor} to delegate calls to that given delegate bean to the decorator chain.
 * 
 * @author Niklas Schlimm
 * 
 */
public class InterceptorAwareBeanPostProcessor implements BeanPostProcessor, InitializingBean {

	@Autowired
	private InterceptorMetaDataBean metaData;

	@Autowired
	private final ConfigurableListableBeanFactory beanFactory = null;

	/**
	 * Chaining strategy in use, may be custom strategy
	 */
//	private DecoratorChainingStrategy chainingStrategy;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		if (metaData.isInterceptedBean(beanName)) {
			return buildInterceptingProxy(bean, beanName);
		} else {
			return bean;
		}
	}

//	@SuppressWarnings("serial")
	public Object buildInterceptingProxy(final Object bean, final String beanName) {
//		final SimpleBeanTargetSource targetSource = new SimpleBeanTargetSource() {{setTargetBeanName(beanName); setTargetClass(bean.getClass()); setBeanFactory(beanFactory);}};
//		ProxyFactory pf = new ProxyFactory() {{setTargetSource(targetSource); setProxyTargetClass(true);}};
//		InterceptorAwareDelegatingMethodInterceptor interceptor = new InterceptorAwareDelegatingMethodInterceptor(chainingStrategy.getChainedDecorators(beanFactory, metaData.getQualifiedDecoratorChain(beanName), bean));
//		pf.addAdvice(interceptor); pf.addInterface(InvocationContextInterceptorProxyInspector.class);
//		Object proxy = pf.getProxy();
//		return proxy;
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
//		if (chainingStrategy == null) {
//			chainingStrategy = new SimpleDecoratorChainingStrategy();
//		}
	}

}
