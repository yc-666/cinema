package org.ltc.cinema;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.ltc.cinema.mapper")
public class CimenaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CimenaApplication.class, args);
    }

}
