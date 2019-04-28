package com.sbcloud.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import com.sbcloud.api.fallCallBack.TestFallBack2;
import com.sbcloud.pojo.ServiceCommPojo;

import feign.Body;
import feign.Param;

@FeignClient(name="eurekaclient",fallbackFactory=TestFallBack2.class)
@Component
public interface TestService extends MyBase{
	@PostMapping("/test")
	public ServiceCommPojo<?> test() ;
	
	@Body(value = "%7B'orderNo:${orderNo}'%7D")//调用最后请求出去的就是{order:1234}
	@PostMapping("/test2")
	public ServiceCommPojo<?> test2(@Param("orderNo")String orderNo) ;
}
 