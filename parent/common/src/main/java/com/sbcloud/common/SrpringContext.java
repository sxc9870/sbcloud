package com.sbcloud.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SrpringContext implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext a) throws BeansException {
		if (applicationContext == null) {
			applicationContext = a;
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
