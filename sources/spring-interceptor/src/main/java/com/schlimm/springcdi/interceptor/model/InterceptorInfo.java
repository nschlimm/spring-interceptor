package com.schlimm.springcdi.interceptor.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.interceptor.Interceptor;
import javax.interceptor.InterceptorBinding;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

public abstract class InterceptorInfo {

	protected static Class<? extends Annotation> interceptorAnnotationType = Interceptor.class;

	protected static Class<? extends Annotation> interceptorBindingAnnotationType = InterceptorBinding.class;

	protected BeanDefinitionHolder beanDefinitionHolder;

	protected AnnotationMetadata annotationMetadata;

	private Set<Method> interceptorMethods = new HashSet<Method>();

	private Set<String> interceptedBeans = new HashSet<String>();

	private List<String> interceptorBindings = new ArrayList<String>();

	/**
	 * Name of the beans where this interceptor is defined on the class level
	 */
	protected List<String> classLevelInterceptions = new ArrayList<String>();

	/**
	 * All methods where this interceptor is defined on the method level
	 */
	protected List<Method> interceptedMethods = new ArrayList<Method>();

	private void resolveInterceptorBindings() {
		Set<String> annotationTypes = annotationMetadata.getAnnotationTypes();
		for (String annotationType : annotationTypes) {
			Set<String> metaAnnotations = annotationMetadata.getMetaAnnotationTypes(annotationType);
			for (String metaAnnotation : metaAnnotations) {
				if (metaAnnotation.equals(interceptorBindingAnnotationType.getName())) {
					getInterceptorBindings().add(annotationType);
				}
			}
		}
	}

	protected abstract void resolveInterceptorMethods();

	public InterceptorInfo(BeanDefinitionHolder beanDefinitionHolder) {
		Assert.isInstanceOf(AnnotatedBeanDefinition.class, beanDefinitionHolder.getBeanDefinition());
		this.beanDefinitionHolder = beanDefinitionHolder;
		this.annotationMetadata = ((AnnotatedBeanDefinition) beanDefinitionHolder.getBeanDefinition()).getMetadata();
		resolveInterceptorMethods();
		resolveInterceptorBindings();
	}

	public BeanDefinitionHolder getBeanDefinitionHolder() {
		return beanDefinitionHolder;
	}

	public void setBeanDefinitionHolder(BeanDefinitionHolder beanDefinitionHolder) {
		this.beanDefinitionHolder = beanDefinitionHolder;
	}

	public static boolean isInterceptor(AnnotatedBeanDefinition abd) {
		Map<String, Object> attributes = abd.getMetadata().getAnnotationAttributes(interceptorAnnotationType.getName());
		if (attributes != null)
			return true;
		return false;
	}

	public AnnotationMetadata getAnnotationMetadata() {
		return annotationMetadata;
	}

	public List<String> getInterceptorBindings() {
		return interceptorBindings;
	}

	public void setInterceptedBeans(Set<String> interceptedBeans) {
		this.interceptedBeans = interceptedBeans;
	}

	public Set<String> getInterceptedBeans() {
		return interceptedBeans;
	}

	public void addInterceptedBean(String beanName) {
		if (!interceptedBeans.contains(beanName))
			interceptedBeans.add(beanName);
	}

	public boolean isInterceptingBean(String beanName) {
		Assert.notNull(beanName);
		for (String interceptedBeanName : interceptedBeans) {
			if (beanName.equals(interceptedBeanName))
				return true;
		}
		return false;
	}

	public void setClassLevelInterceptions(List<String> classLevelInterceptions) {
		this.classLevelInterceptions = classLevelInterceptions;
	}

	public List<String> getClassLevelInterceptions() {
		return classLevelInterceptions;
	}

	public void addClassLevelInterception(String beanName) {
		if (!classLevelInterceptions.contains(beanName))
			classLevelInterceptions.add(beanName);
	}

	public void addInterceptedMethod(Method method) {
		if (!interceptedMethods.contains(method))
			interceptedMethods.add(method);
	}

	public List<Method> getInterceptedMethods() {
		return interceptedMethods;
	}

	public boolean matches(String beanName, Method givenMethod) {
		for (Method interceptedMethod : interceptedMethods) {
			if (givenMethod.equals(interceptedMethod)) {
				return true;
			}
		}
		for (String interceptedBeanName : classLevelInterceptions) {
			if (interceptedBeanName.equals(beanName)) {
				return true;
			}
		}
		return false;
	}

	public void setInterceptorMethods(Set<Method> interceptorMethods) {
		this.interceptorMethods = interceptorMethods;
	}

	public Set<Method> getInterceptorMethods() {
		return interceptorMethods;
	}

	public String toString() {
		return beanDefinitionHolder.toString();
	}

}
