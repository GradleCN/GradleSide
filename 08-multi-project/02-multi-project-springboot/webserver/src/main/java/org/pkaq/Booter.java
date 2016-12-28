package org.pkaq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description:     应用配置启动入口.
 * @FileName:         Booter.java
 * @Author:            S.PKAQ
 * @Version:          1.0
 * @Date:              2016年4月7日 上午9:28:07
 */
@SpringBootApplication
@Slf4j
public class Booter implements CommandLineRunner {
    /**
     * 入口函数.
     * @param args args
     */
    @Autowired
    public static void main(String[] args) {
        SpringApplication.run(Booter.class, args);
    }

    public void run(String... args) throws Exception {
        log.info("  --- --- --- [ web started ] --- --- ---  ");
    }
}
