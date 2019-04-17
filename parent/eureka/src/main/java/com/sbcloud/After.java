package com.sbcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class After implements CommandLineRunner {

	@Value("${cloud}")
	private String cloud;
	@Value("${profile}")
	private String profile;

	@Value("${application}")
	private String application;

	@Value("${bootstrap}")
	private String bootstrap;
	@Value("${spring.profiles.active}")
	private String name;



	@Override
	public void run(String... args) throws Exception {
		System.out.println("cloud:" + cloud);
		System.out.println("profile:" + profile);
		System.out.println("application:" + application);
		System.out.println("bootstrap:" + bootstrap);
		System.out.println("name:" + name);
	}

}
