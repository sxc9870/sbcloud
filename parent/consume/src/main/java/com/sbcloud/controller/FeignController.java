package com.sbcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbcloud.common.api.TestService;

@RestController
public class FeignController {

	@Autowired
	private TestService testService;
 

	@GetMapping("/AAAAAAuth")
	public ResponseEntity<?> ftest6() throws Exception {
		throw new Exception();
	}

}
