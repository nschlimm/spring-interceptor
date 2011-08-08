package com.schlimm.springcdi.interceptor.ct._91_C1.bindingtypes;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.inject.Stereotype;

@Transactional
@Stereotype
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Action { }
