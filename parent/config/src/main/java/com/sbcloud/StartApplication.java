package com.sbcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableConfigServer
//@EnableEurekaClient
public class StartApplication {

	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}
    @RequestMapping("")
    public String get(){
        return "index";
    }
}
