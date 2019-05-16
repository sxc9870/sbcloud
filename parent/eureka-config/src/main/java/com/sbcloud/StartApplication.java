package com.sbcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

import zipkin2.server.internal.EnableZipkinServer;

//import zipkin2.server.internal.EnableZipkinServer;

@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableTurbine
@EnableZipkinServer
public class StartApplication   {
//	@Bean
//	public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet() {
//		HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
//		ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<>(
//				streamServlet);
//		registrationBean.setLoadOnStartup(1);
//		registrationBean.addUrlMappings("/actuator/hystrix.stream");
//		registrationBean.setName("HystrixMetricsStreamServlet");
//		return registrationBean;
//	}
	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}

	//@Override
	//protected void configurePathMatch(PathMatchConfigurer configurer) {
		//super.configurePathMatch(configurer);
//		configurer.addPathPrefix("/zipkin", new Predicate<Class<?>>() {
//			@Override
//			public boolean test(Class<?> t) {
//				return t.getName().contains("ZipkinUiAutoConfiguration");
//			}
//		});

	//}

}
