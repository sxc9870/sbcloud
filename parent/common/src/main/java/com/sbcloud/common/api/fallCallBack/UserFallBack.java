package com.sbcloud.common.api.fallCallBack;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.stereotype.Component;

import com.sbcloud.common.SrpringContext;
import com.sbcloud.common.api.FallBackInterface;

import feign.hystrix.FallbackFactory;

/**
 * 定义降级方法
 * 
 * @author Administrator
 *
 */
@Component
public class UserFallBack implements FallbackFactory<FallBackInterface>  {
	private Object proxy;
	private ThreadLocal<Throwable> local = new ThreadLocal<>();;

	@Override
	public FallBackInterface create(Throwable cause) {
		local.set(cause);
		return (FallBackInterface) proxy;
	}

	public UserFallBack() {
		String[] str = SrpringContext.getApplicationContext().getBeanNamesForType(FallBackInterface.class);
		Class<?>[] classs = new Class[str.length];
		for (int i = 0; i < str.length; i++) {
			try {
				classs[i] = Class.forName(str[i]);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		InvocationHandler invocationHandler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return null;
			}
		};
		this.proxy = Proxy.newProxyInstance(getClass().getClassLoader(), classs, invocationHandler);
		System.out.println("UserFallBack init ");
	}
 

}
