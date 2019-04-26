package com.sbcloud.services.impl;

import java.util.Random;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.RestController;

import com.sbcloud.api.TestService;
import com.sbcloud.dao.TargetRepository;
import com.sbcloud.pojo.TestModel;

@RestController
public class TestServiceImpl implements TestService{

	  // @Resource是根据名称注入 @Autowired是根据类型注入
    @Resource
    private TargetRepository targetRepository;

	@Value("${server.port}")
	private String aaa;
	@Override
	@Transactional
	public TestModel test() {
		System.out.println("进入test");
		try {
			int i=new Random().nextInt(4000);
			System.out.println("休眠"+i);
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestModel t=new TestModel();
        t.setTname(aaa);
		
      //  targetRepository.save(t);
        System.out.println("退出test");
		return t;
	}
	@Override
	public TestModel test2(String orderNo) {
		// TODO Auto-generated method stub
		return null;
	}

}
