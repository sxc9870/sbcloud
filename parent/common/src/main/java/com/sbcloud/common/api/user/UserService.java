package com.sbcloud.common.api.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import com.sbcloud.common.api.FallBackInterface;
import com.sbcloud.common.api.fallCallBack.UserFallBack;
import com.sbcloud.common.pojo.ServiceCommPojo;


@FeignClient(name="eurekaclient",fallbackFactory=UserFallBack.class)
@Component
public interface UserService extends FallBackInterface{
	@PostMapping("/doLogin")
	public ResponseEntity<?> doLogin() ;
}
 