package com.sbcloud;

import java.io.IOException;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestTransformer;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
@Component
public class MyIntecetper implements ClientHttpRequestInterceptor,LoadBalancerRequestTransformer{

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
	  System.out.println("???");
		return null;
	}

	@Override
	public HttpRequest transformRequest(HttpRequest request, ServiceInstance instance) {
		return request;
	}

}
