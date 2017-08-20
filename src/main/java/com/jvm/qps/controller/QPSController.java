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
        long startTimeInNano = System.nanoTime();

        double d = 0D;
        for (int index = 0;index < 1000000;index ++){
            d *= index;
        }

        logger.info("线程名称：{}，线程ID：{},请求时间：{}",
                Thread.currentThread().getName(),
                Thread.currentThread().getId(),
                System.nanoTime() - startTimeInNano);

        return "I am fine!Thank you!and you?";
    }

    @PostMapping("/helloWithHesitation1000Millis")
    public String helloWithHesitation1000Millis() throws Exception{
        long startTimeInNano = System.nanoTime();

        //模拟比较长的操作，比如io，复杂的数据计算等
        Thread.sleep(1000);

        logger.info("线程名称：{}，线程ID：{},请求时间：{}",
                Thread.currentThread().getName(),
                Thread.currentThread().getId(),
                System.nanoTime() - startTimeInNano);

        return "I am fine!Thank you!and you?";
    }


}
