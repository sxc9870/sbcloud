package com.sbcloud;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope // 自动刷新配置变更
@EnableDiscoveryClient
@ComponentScan("com.sbcloud")
//@EnableCircuitBreaker // 断路器
@EnableHystrix  //继承上面EnableCircuitBreaker
//@RibbonClient(name="default.foo",configuration = Foo.class)
public class StartApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// EurekaRibbonClientConfiguration
		// RibbonAutoConfiguration HystrixCircuitBreakerConfiguration
		 SpringApplication.run(StartApplication.class, args);
	//	ExecutorService pool = Executors.newFixedThreadPool(1);

	}

	@Bean
	@LoadBalanced // 不加无法使用ribbon的负载均衡
	public RestTemplate restTemplate(MyIntecetper MyIntecetper) {
		RestTemplate restTemplate = new RestTemplate();
//		// 把自定义的ClientHttpRequestInterceptor添加到RestTemplate，可添加多个
//		restTemplate.setInterceptors(Collections.singletonList(MyIntecetper));

		return restTemplate;
	}

}
