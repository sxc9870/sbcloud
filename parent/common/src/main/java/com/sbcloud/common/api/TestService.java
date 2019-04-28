package com.sbcloud.common.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import com.sbcloud.common.api.fallCallBack.TestFallBack2;
import com.sbcloud.common.pojo.ServiceCommPojo;

import feign.Body;
import feign.Param;

@FeignClient(name="eurekaclient",fallbackFactory=TestFallBack2.class)
@Component
public interface TestService extends MyBase{
	@PostMapping("/test")
	public ServiceCommPojo<?> test() ;
}
 