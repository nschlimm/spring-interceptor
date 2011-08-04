package com.schlimm.springcdi.interceptor.processor;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("/test-context-interceptor-simple.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class InterceptorAwareBeanPostProcessorTest {

}
