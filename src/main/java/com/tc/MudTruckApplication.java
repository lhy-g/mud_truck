package com.tc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.tc.mud.applet.mapper"})  
public class MudTruckApplication {

    public static void main(String[] args) {
        SpringApplication.run(MudTruckApplication.class, args);
        System.out.println("MudTruckApplication 启动成功!!!!!!!!!!");
    }

}
