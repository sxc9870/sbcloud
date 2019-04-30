package com.sbcloud.services.impl;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sbcloud.common.api.user.UserService;
import com.sbcloud.dao.TargetRepository;

@RestController
public class TestServiceImpl implements UserService {

	// @Resource是根据名称注入 @Autowired是根据类型注入
	@Resource
	private TargetRepository targetRepository;
	
	@Autowired
	private UserService userService;
	@Value("${server.port}")
	private String aaa;
	
	
	@HystrixCommand(fallbackMethod = "fall")
	@GetMapping("/getPort2")
	public Object aaa() throws InterruptedException {
		System.out.println("正常进入");
		Thread.sleep(4000);
		System.out.println("正常退出");
		return aaa;
	}
	@GetMapping("/getPort3")
	public Object aaa3() throws InterruptedException {
		return aaa+":"+userService.test().getBody();
	}
	public Object fall() {
		System.out.println("降级!!");
		return new String("\"降级!!!!\"");
	}


	@Override
	public ResponseEntity<String> doLogin() {
		return null;
	}
	@Override
	public ResponseEntity<String> test() {
		System.out.println("test"); 
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}System.out.println("test2"); 
		return ResponseEntity.ok("test");
	}
}
