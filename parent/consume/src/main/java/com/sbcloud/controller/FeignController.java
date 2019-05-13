package com.sbcloud.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.client.http.HttpRequest;
import com.sbcloud.common.api.user.UserService;

@RestController
public class FeignController {

	@Autowired
	private UserService testService;

	@GetMapping("/AAAAAAuth")
	public ResponseEntity<?> ftest6() throws Exception {
		throw new Exception();
	}

	@GetMapping("/test")
	public ResponseEntity<?> test(HttpServletRequest req) throws Exception {
		System.out.println(req.getHeaderNames());
		return testService.test();
	}
}
