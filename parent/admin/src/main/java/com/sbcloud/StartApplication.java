package com.sbcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableAdminServer
public class StartApplication {
	 
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 SpringApplication.run(StartApplication.class, args);
	}
}
