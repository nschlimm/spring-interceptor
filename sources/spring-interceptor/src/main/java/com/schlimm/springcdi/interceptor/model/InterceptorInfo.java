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
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

/**
 * Model bean that stores all relevant interceptor information. One bean per interceptor defined in the bean factory.
 * 
 * Subclasses can implement logic specific to {@link BeanDefinition} type, such as {@link AnnotatedBeanDefinition} or
 * {@link GenericBeanDefinition}. Also subclasses can implement logic specific to JSR 318 interceptor types, such as method 
 * interceptors or time out interceptors.
 * 
 * @author Niklas Schlimm
 * 
 */
public abstract class InterceptorInfo {

	public static Class<? extends Annotation> interceptorAnnotationType = Interceptor.class;

	public static Class<? extends Annotation> interceptorBindingAnnotationType = InterceptorBinding.class;

	/**
	 * Bean definition of this interceptor
	 */
	protected BeanDefinitionHolder beanDefinitionHolder;

	/**
	 * Annotation meta data for this interceptor
	 */
	protected AnnotationMetadata annotationMetadata;

	/**
	 * All interceptor methods of this interceptor class
	 */
	private Set<Method> interceptorMethods = new HashSet<Method>();

	/**
	 * Names of the beans that this interceptor applies to
	 */
	private Set<String> interceptedBeans = new HashSet<String>();

	/**
	 * Bindings declared on this interceptor
	 */
	private List<String> interceptorBindings = new ArrayList<String>();

	/**
	 * Name of the beans where this interceptor is defined on the class level
	 */
	protected List<String> classLevelInterceptions = new ArrayList<String>();

	/**
	 * All methods where this interceptor is defined on the method level
	 */
	protected List<Method> interceptedMethods = new ArrayList<Method>();

	public static boolean isInterceptor(AnnotatedBeanDefinition abd) {
		Map<String, Object> attributes = abd.getMetadata().getAnnotationAttributes(interceptorAnnotationType.getName());
		if (attributes != null)
			return true;
		return false;
	}

	protected void resolveInterceptorBindings() {
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

	/**
	 * Check if the given bean is intercepted by this interceptor
	 * 
	 * @param beanName
	 *            name of the bean in the factory
	 * @return true, if this interceptor intercepts the bean
	 */
	public boolean isInterceptingBean(String beanName) {
		Assert.notNull(beanName);
		for (String interceptedBeanName : interceptedBeans) {
			if (beanName.equals(interceptedBeanName))
				return true;
		}
		return false;
	}

	/**
	 * Check is this interceptor intercepts the bean on the class level or on the method level.
	 * 
	 * @param beanName
	 *            name of the bean
	 * @param givenMethod
	 *            method that may be intercepted
	 * 
	 * @return true if this interceptor intercepts the (whole) bean or the method
	 */
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

	/**
	 * Subclasses can implement custom logic to resolve interceptor methods on this interceptor
	 */
	protected abstract void resolveInterceptorMethods();

	public BeanDefinitionHolder getBeanDefinitionHolder() {
		return beanDefinitionHolder;
	}

	public void setBeanDefinitionHolder(BeanDefinitionHolder beanDefinitionHolder) {
		this.beanDefinitionHolder = beanDefinitionHolder;
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

	public void setInterceptorMethods(Set<Method> interceptorMethods) {
		this.interceptorMethods = interceptorMethods;
	}

	public Set<Method> getInterceptorMethods() {
		return interceptorMethods;
	}

	public String toString() {
		return beanDefinitionHolder.toString();
	}

	public Map<String, Object> getAnnotationAttributes(String annotationType) {
		return annotationMetadata.getAnnotationAttributes(annotationType);
	}

}
