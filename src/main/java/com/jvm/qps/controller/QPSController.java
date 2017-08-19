package com.jvm.qps.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qps")
public class QPSController {
    private static final Logger logger = LoggerFactory.getLogger(QPSController.class);

    @PostMapping("/helloWithoutHesitation")
    public String helloWithoutHesitation() throws Exception{
        logger.info("线程名称：{}，线程ID：{}",Thread.currentThread().getName(),Thread.currentThread().getId());

        return "I am fine!Thank you!and you?";
    }

    @PostMapping("/helloWithHesitation1000Millis")
    public String helloWithHesitation1000Millis() throws Exception{
        logger.info("线程名称：{}，线程ID：{}",Thread.currentThread().getName(),Thread.currentThread().getId());

        Thread.sleep(1000);

        return "I am fine!Thank you!and you?";
    }
}
