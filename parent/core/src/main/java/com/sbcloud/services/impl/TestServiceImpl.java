package com.sbcloud.services.impl;

import java.util.Random;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sbcloud.api.TestService;
import com.sbcloud.dao.TargetRepository;
import com.sbcloud.pojo.ServiceCommPojo;
import com.sbcloud.pojo.TestModel;

@RestController
public class TestServiceImpl implements TestService {

	// @Resource是根据名称注入 @Autowired是根据类型注入
	@Resource
	private TargetRepository targetRepository;

	@Value("${server.port}")
	private String aaa;

	@Override
	@Transactional
	public ServiceCommPojo test() {
		System.out.println("进入test");
		try {
			int i = new Random().nextInt(1000) + 2500;
			System.out.println("休眠" + i);
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestModel t = new TestModel();
		t.setTname(aaa);

		// targetRepository.save(t);
		System.out.println("退出test");
		return null;
	}

	@Override
	public ServiceCommPojo test2(String orderNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@HystrixCommand(fallbackMethod = "fall")
	@GetMapping("/getPort2")
	public Object aaa() throws InterruptedException {
		System.out.println("正常进入");
		Thread.sleep(4000);
		System.out.println("正常退出");
		return aaa;
	}

	public Object fall() {
		System.out.println("降级!!");
		return new String("\"降级!!!!\"");
	}
}
