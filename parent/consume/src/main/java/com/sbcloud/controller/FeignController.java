package com.sbcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbcloud.api.TestService;
import com.sbcloud.api.TestService3;
import com.sbcloud.pojo.TestModel;

@RestController
public class FeignController {
	 

	@Autowired
	private TestService testService;
	@Autowired
	private TestService3 testService3;
	@GetMapping("/ftest")
	public TestModel ftest() throws InterruptedException {
		System.out.println("进入ftest");
		TestModel t = testService.test();
		System.out.println("退出ftest");
		return t;
	}
	@GetMapping("/ftest3")
	public TestModel ftest3() throws InterruptedException {
		System.out.println("进入ftest");
		TestModel t = testService3.test();
		System.out.println("退出ftest");
		return t;
	}
}
