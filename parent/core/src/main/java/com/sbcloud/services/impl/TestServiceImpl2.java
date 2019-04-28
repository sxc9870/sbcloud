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
import com.sbcloud.api.TestService3;
import com.sbcloud.dao.TargetRepository;
import com.sbcloud.pojo.ServiceCommPojo;
import com.sbcloud.pojo.TestModel;

@RestController
public class TestServiceImpl2 implements TestService3 {

	@Value("${server.port}")
	private String aaa;

	@Override
	public ServiceCommPojo<?> test() {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
