package com.sbcloud.common.api.fallCallBack;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.sbcloud.common.api.FallBackInterface;

import feign.hystrix.FallbackFactory;

/**
 * 定义降级方法
 * 
 * @author Administrator
 *
 */
@Component
public class UserFallBack implements FallbackFactory<FallBackInterface> {
	private Object proxy;
	private ThreadLocal<Throwable> local = new ThreadLocal<>();;

	@Value("spring.application.name")
	private String appName;
	@Override
	public FallBackInterface create(Throwable cause) {
		local.set(cause);
		return (FallBackInterface) proxy;
	}

	public UserFallBack(ApplicationContext a) {
		System.out.println("UserFallBack");
		String[] str = a.getBeanNamesForType(FallBackInterface.class);
		List<Class<?>> classs = new ArrayList<>();
		for (int i = 0; i < str.length; i++) {
			try {
				classs.add(Class.forName(str[i]));
			} catch (ClassNotFoundException e) {
			}
		}
		InvocationHandler invocationHandler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				System.out.println(appName+"降級!!");
				String a = local.get().getMessage();
				if (a == null) {
					a = local.get().toString();
				} else if (a.contains("Load balancer does not have available server for client: ")) {
					a = "沒有找到可用服务,请稍后重试";
				} else if (a.contains("Hystrix circuit short-circuited and is OPEN")) {
					a = "断路器打开中,请稍后重试";
				}else if (a.contains("Read timed out")) {
					a = "请求超时";
				}else if(a.contains("HystrixTimeoutException")) {
					a = "断路器超时";
				}
				return new ResponseEntity<>(a, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		};
		this.proxy = Proxy.newProxyInstance(getClass().getClassLoader(), classs.toArray(new Class[0]),
				invocationHandler);
		System.out.println("UserFallBack init ");
	}

}
