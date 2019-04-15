package com.sbcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class StartApplication {

	public static void main(String[] args) {
		
		
		//System.out.println(System.getProperty("user.dir"));
		//C:\Users\Administrator\git\repository\parent\config

		SpringApplication.run(StartApplication.class, args);
	}

}
