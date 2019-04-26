//package com.sbcloud.api;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import com.sbcloud.api.fallCallBack.TestFallBack;
//import com.sbcloud.pojo.TestModel;
//
//import feign.Body;
//import feign.Headers;
//import feign.Param;
//
////@FeignClient(name="eurekaclient2",fallback=TestFallBack.class)
////@Component
////@Headers("Accept:application/json")//全局允许某个请求头
////public interface TestService2 {
////	@PostMapping("/test2")
////	@Headers("Content-Type:application/json")
////	public TestModel test() ;
////	
////	@PostMapping("/test3")
////	@Headers("Token:{token}")//请求头内携带token
////	public TestModel test2(@Param("token")String token) ;
////	
////}
//// 