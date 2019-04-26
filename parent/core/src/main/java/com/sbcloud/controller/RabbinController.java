package com.sbcloud.controller;
//package com.sbcloud;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//@RestController
//public class RabbinController {
//	@Value("${server.port}")
//	private String aaa;
//
//	@Autowired
//	private RestTemplate restTemplate;
//	
//	
//	@GetMapping("/")
//	public Object a() {
//		Object o=	restTemplate.getForObject("http://eurekaclient/getPort", Object.class);
//		return o;
//	}
//	
//	@GetMapping("/getPort")
//	public Object aaa() {
//		 
//		return aaa;
//	}
//	
//	
//}
