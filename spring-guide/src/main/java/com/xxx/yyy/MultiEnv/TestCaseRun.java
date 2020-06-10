package com.xxx.yyy.MultiEnv;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(locations = {
        "classpath*:applicationBean.xml"})
public abstract class TestCaseRun extends AbstractTestNGSpringContextTests {

}
