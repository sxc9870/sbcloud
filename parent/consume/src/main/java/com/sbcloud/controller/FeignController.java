package com.sbcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbcloud.api.TestService;
import com.sbcloud.api.TestService3;
import com.sbcloud.pojo.ServiceCommPojo;

@RestController
public class FeignController {

	@Autowired
	private TestService testService;
	@Autowired
	private TestService3 testService3;

	@GetMapping("/ftest")
	public ServiceCommPojo<?> ftest() throws InterruptedException {
		System.out.println("进入ftest");
		ServiceCommPojo<?> a = testService.test();
		System.out.println("退出ftest");
		return null;
	}

	@GetMapping("/ftest3")
	public ServiceCommPojo<?> ftest3() throws InterruptedException {
		System.out.println("进入ftest");
		ServiceCommPojo<?> t = testService3.test();
		System.out.println("退出ftest");
		return t;
	}

	@GetMapping("/zuulTest")
	public ResponseEntity<?> ftest4() throws InterruptedException {
		ServiceCommPojo<?> t = new ServiceCommPojo();
		return ResponseEntity.ok(t);
	}

	@GetMapping("/xxxxxAuth")
	public ResponseEntity<?> ftest5() throws InterruptedException {
		System.out.println("进入xxxxxAuth");
		ServiceCommPojo<?> t = new ServiceCommPojo();

		System.out.println("退出xxxxxAuth");
		return ResponseEntity.ok(t);
	}

	@GetMapping("/AAAAAAuth")
	public ResponseEntity<?> ftest6() throws Exception {
		throw new Exception();
	}

}
