package com.sbcloud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sbcloud.MyHystrixCommanMode;
import com.sbcloud.hystrix.TestCollasperCommand;

@RestController
public class HystrixController {
	@Value("${server.port}")
	private String aaa;

	@Autowired
	private RestTemplate restTemplate;

	public Map<String, List<String>> query(List<String> ids) throws InterruptedException {
		Map<String, List<String>> o = new HashMap<>();
		for (String str : ids) {
			o.put(str, getArray(str));
		}
		return o;

	}

	private List<String> getArray(String str) {
		List<String> o = new ArrayList<>();
		o.add(str);
		return o;
	}

	private static volatile int i = 0;

	@GetMapping("/b")
	public Object b() throws InterruptedException, ExecutionException {
		int a = i++;
		System.out.println(a);
		List<String> list = new TestCollasperCommand(a + "", this).queue().get();
		System.out.println(list.get(0));
		return list;
	}

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
