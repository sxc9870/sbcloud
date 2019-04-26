//package com.sbcloud.api.fallCallBack;
//
//import org.springframework.stereotype.Component;
//
//import com.sbcloud.api.TestService;
//import com.sbcloud.pojo.TestModel;
//
///**
// * 定义降级方法
// * @author Administrator
// *
// */
//@Component
//public class TestFallBack implements TestService{
//	public TestFallBack() {
//
//	System.out.println("TestFallBack");
//	}
//	@Override
//	public TestModel test() {
//		TestModel t=new TestModel();
//		t.setTname("降级!!");
//		return t;
//	}
//	 
//	@Override
//	public TestModel test2(String orderNo) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
