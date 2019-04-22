package com.sbcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sbcloud.MyHystrixCommanMode;

@RestController
public class HystrixController {
	@Value("${server.port}")
	private String aaa;

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/h")
	public Object a() throws InterruptedException {
		return new MyHystrixCommanMode("a", restTemplate).execute();
	}

	@GetMapping("/a")
	public Object a2() throws InterruptedException {
		Object o = restTemplate.getForObject("http://eurekaclient/getPort2", Object.class);
		return o;
	}

	public Object fall() {
		System.out.println("降级!!");
		return new String("\"降级!!!!\"");
	}

	@HystrixCommand(fallbackMethod = "fall", threadPoolProperties = { @HystrixProperty(name = "coreSize", value = "1"),
			@HystrixProperty(name = "queueSizeRejectionThreshold", value = "1") }, commandProperties = {
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000") })
	@GetMapping("/getPort2")
	public Object aaa() throws InterruptedException {
		System.out.println("正常进入");
		Thread.sleep(4000);
		System.out.println("正常退出");
		return aaa;
	}
}
