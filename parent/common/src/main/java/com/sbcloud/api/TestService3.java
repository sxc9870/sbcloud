package com.sbcloud.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import com.sbcloud.api.fallCallBack.TestFallBack2;
import com.sbcloud.pojo.ServiceCommPojo;

@FeignClient(name="eurekaclient",fallbackFactory=TestFallBack2.class)
@Component
public interface TestService3 extends MyBase{
	@PostMapping("/test3")
	public ServiceCommPojo<?> test() ;
}
 