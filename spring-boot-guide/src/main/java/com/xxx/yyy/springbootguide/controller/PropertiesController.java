package com.xxx.yyy.springbootguide.controller;

import com.xxx.yyy.springbootguide.model.MyProperties1;
import com.xxx.yyy.springbootguide.model.MyProperties2;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: maoyan
 * @date: 2020/2/4 14:59:19
 * @description:
 */
//@Log4j2
@RequestMapping("/properties")
@RestController
public class PropertiesController {
    private static final Logger log = LoggerFactory.getLogger(PropertiesController.class);

    @Autowired
    private MyProperties1 myProperties1;

    @Autowired
    private MyProperties2 myProperties2;

    @GetMapping("/1")
    public MyProperties1 myProperties1() {
        log.info("=================================================================================================");
        log.info(myProperties1.toString());
        log.info("=================================================================================================");
        return myProperties1;
    }

    @GetMapping("/2")
    public MyProperties2 myProperties2() {
        log.info("=================================================================================================");
        log.info(myProperties2.toString());
        log.info("=================================================================================================");

        String st = null;
        try {
            log.info("This is info");
            Double d = 1/Double.valueOf(st);
            System.out.println(d);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        log.error("this is error");
        log.warn("this is warn");
        return myProperties2;
    }


}
