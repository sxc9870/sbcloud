package com.sbcloud;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sxc.zuul.filters")
public class MyZuulConfig {
	private List<String> needLoginRoutes;

	public List<String> getNeedLoginRoutes() {
		return needLoginRoutes;
	}

	public void setNeedLoginRoutes(List<String> needLoginRoutes) {
		this.needLoginRoutes = needLoginRoutes;
	}

	private List<String> noAuthenticationRoutes;

	public List<String> getNoAuthenticationRoutes() {
		return noAuthenticationRoutes;
	}

	public void setNoAuthenticationRoutes(List<String> noAuthenticationRoutes) {
		this.noAuthenticationRoutes = noAuthenticationRoutes;
	}
}
