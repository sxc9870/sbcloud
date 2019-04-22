package com.sbcloud;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.cloud.netflix.ribbon.eureka.EurekaRibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.NoOpPing;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.niws.loadbalancer.NIWSDiscoveryPing;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope // 自动刷新配置变更 
@EnableDiscoveryClient
@ComponentScan("com.sbcloud")
@EnableCircuitBreaker  //断路器
//@RibbonClient(name="default.foo",configuration = Foo.class)
public class StartApplication {

	public static void main(String[] args) {
		//EurekaRibbonClientConfiguration
		//RibbonAutoConfiguration
		SpringApplication.run(StartApplication.class, args);
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
