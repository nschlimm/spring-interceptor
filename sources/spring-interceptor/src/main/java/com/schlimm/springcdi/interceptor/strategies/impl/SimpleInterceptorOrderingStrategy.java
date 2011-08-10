package com.schlimm.springcdi.interceptor.strategies.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.schlimm.springcdi.interceptor.model.InterceptorInfo;
import com.schlimm.springcdi.interceptor.model.InterceptorOrder;
import com.schlimm.springcdi.interceptor.strategies.InterceptorOrderingStrategy;

public class SimpleInterceptorOrderingStrategy implements InterceptorOrderingStrategy {

	@Override
	public List<InterceptorInfo> orderInterceptors(BeanFactory beanFactory, List<InterceptorInfo> interceptors) {
		InterceptorOrder orderBean = null;
		if (BeanFactoryUtils.beanNamesForTypeIncludingAncestors((ConfigurableListableBeanFactory)beanFactory, InterceptorOrder.class).length>0) {
			orderBean = BeanFactoryUtils.beanOfType((ConfigurableListableBeanFactory)beanFactory, InterceptorOrder.class);
			return sortInterceptors(orderBean, interceptors);
		} else {
			return interceptors;
		}
	}

	@SuppressWarnings("rawtypes")
	private List<InterceptorInfo> sortInterceptors(InterceptorOrder orderBean, List<InterceptorInfo> unsortedInterceptors) {
		List<InterceptorInfo> sortedInterceptors = new ArrayList<InterceptorInfo>();
		for (Class interceptorClass : orderBean.getOrdererInterceptorClasses()) {
			for (InterceptorInfo interceptorInfo : unsortedInterceptors) {
				if (interceptorInfo.getBeanDefinitionHolder().getBeanDefinition().getBeanClassName().equals(interceptorClass.getName())) {
					sortedInterceptors.add(interceptorInfo);
				}
			}
		}
		List<InterceptorInfo> unorderedInterceptors = new ArrayList<InterceptorInfo>();
		for (InterceptorInfo interceptorInfo : unsortedInterceptors) {
			if (!sortedInterceptors.contains(interceptorInfo)) {
				unorderedInterceptors.add(interceptorInfo);
			}
		}
		sortedInterceptors.addAll(unorderedInterceptors);
		return sortedInterceptors;
	}

}
