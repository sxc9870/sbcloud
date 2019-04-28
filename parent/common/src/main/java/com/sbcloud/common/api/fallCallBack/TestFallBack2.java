package com.sbcloud.common.api.fallCallBack;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.sbcloud.common.api.MyBase;

import feign.hystrix.FallbackFactory;

/**
 * 定义降级方法
 * 
 * @author Administrator
 *
 */
@Component
public class TestFallBack2 implements FallbackFactory<MyBase>, ApplicationContextAware {
	private Object proxy;
	private ThreadLocal<Throwable> local = new ThreadLocal<>();;

	@Override
	public MyBase create(Throwable cause) {
		local.set(cause);
		return (MyBase) proxy;
	}

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		String[] str = applicationContext.getBeanNamesForType(MyBase.class);
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
	}

}
