package com.sbcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbcloud.api.TestService;
import com.sbcloud.pojo.TestModel;

@RestController
public class FeignController {
	@Value("${server.port}")
	private String aaa;

	@Autowired
	private TestService testService;

	@GetMapping("/ftest")
	public TestModel ftest() throws InterruptedException {
		return testService.test();
	}
 
}
