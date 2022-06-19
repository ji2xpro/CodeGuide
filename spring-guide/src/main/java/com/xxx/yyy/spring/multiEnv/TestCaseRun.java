package com.xxx.yyy.spring.multiEnv;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(locations = {
        "classpath*:applicationBean.xml"})
public abstract class TestCaseRun extends AbstractTestNGSpringContextTests {

    @Value("${host}")
    public String host;

    @Value("${url}")
    public String url;
}
