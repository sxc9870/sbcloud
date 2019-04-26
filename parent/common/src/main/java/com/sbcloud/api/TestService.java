package com.sbcloud.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sbcloud.api.fallCallBack.TestFallBack;
import com.sbcloud.pojo.TestModel;

import feign.Body;
import feign.Param;

@FeignClient(name="eurekaclient",fallback=TestFallBack.class)
@Component
public interface TestService {
	@PostMapping("/test")
	public TestModel test() ;
	
	@Body(value = "%7B'orderNo:${orderNo}'%7D")//调用最后请求出去的就是{order:1234}
	@PostMapping("/test2")
	public TestModel test2(@Param("orderNo")String orderNo) ;
	
}
 