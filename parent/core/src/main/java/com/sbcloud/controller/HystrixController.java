package com.sbcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sbcloud.MyHystrixCommanMode;

@RestController
public class HystrixController {
	@Value("${server.port}")
	private String aaa;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/h")
	public Object a() throws InterruptedException {
		return new MyHystrixCommanMode("a",restTemplate).execute();
	}

	@GetMapping("/getPort2")
	public Object aaa() throws InterruptedException {
		System.out.println("正常进入");
		Thread.sleep(3000);
		System.out.println("正常退出");
		return aaa;
	}
}
