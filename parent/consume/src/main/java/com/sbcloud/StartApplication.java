package com.sbcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@ComponentScan("com.sbcloud")
//@EnableCircuitBreaker // 断路器
//@EnableHystrix  //继承上面EnableCircuitBreaker
//@RibbonClient(name="default.foo",configuration = Foo.class)
@EnableFeignClients
public class StartApplication {
	@Bean
	public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet() {
		HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
		ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<>(
				streamServlet);
		registrationBean.setLoadOnStartup(1);
		registrationBean.addUrlMappings("/actuator/hystrix.stream");
		registrationBean.setName("HystrixMetricsStreamServlet");
		return registrationBean;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// EurekaRibbonClientConfiguration
		// RibbonAutoConfiguration HystrixCircuitBreakerConfiguration
		SpringApplication.run(StartApplication.class, args);
		// ExecutorService pool = Executors.newFixedThreadPool(1);
	}

//	@Bean 有fegin后不需要,重试机制也不需要
//	@LoadBalanced // 不加无法使用ribbon的负载均衡
//	public RestTemplate restTemplate(MyIntecetper MyIntecetper) {
//		RestTemplate restTemplate = new RestTemplate();
////		// 把自定义的ClientHttpRequestInterceptor添加到RestTemplate，可添加多个
////		restTemplate.setInterceptors(Collections.singletonList(MyIntecetper));
//
//		return restTemplate;
//	}

}
