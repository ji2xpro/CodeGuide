package com.xxx.yyy.spring.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @Auther: maoyan
 * @Date: 2019/3/13 16:43
 * @Description:
 */
public class LogUtil {
    private Logger log;

    private LogUtil(String name) {
        this.configLogProperties();
        this.log = Logger.getLogger(name);
    }

    private LogUtil(Class<?> clazz) {
        this.configLogProperties();
        this.log = Logger.getLogger(clazz);
    }

    public static LogUtil getLogger(String name) {
        return new LogUtil(name);
    }

    public static LogUtil getLogger(Class<?> clazz) {
        return new LogUtil(clazz);
    }

    public void error(Object message) {
        this.log.error(message);
    }

    public void info(Object message) {
        this.log.info(message);
    }

    public void debug(Object message) {
        log.debug(message);
    }

    public void warn(Object message) {
        log.error(message);
    }

    private void configLogProperties() {
//        try {
//            TkConf.writeConf();
//            PropertyConfigurator.common(TkConf.Log4jConf);

//            PropertyConfigurator.common(this.getClass().getClassLoader().getResource("groupConf.properties"));
        PropertyConfigurator.configure(LogUtil.class.getClassLoader().getResource("log4j.properties").getPath());



//        } catch (Exception var2) {
//            ;
//        }

    }
}
