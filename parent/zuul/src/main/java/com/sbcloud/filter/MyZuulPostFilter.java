package com.sbcloud.filter;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sbcloud.MyZuulConfig;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class MyZuulPostFilter extends ZuulFilter {
	private MyZuulConfig yyZuulConfig;
	public MyZuulPostFilter(MyZuulConfig m) {
		this.yyZuulConfig=m;
	}
	/**
	 * 是否生效
	 */
	@Override
	public boolean shouldFilter() {
		return  true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx=RequestContext.getCurrentContext();
		HttpServletResponse ss=	ctx.getResponse();
		ss.getStatus();
		return null;
	}
	
	/**
	 * 定义拦截器类型 pre,post,route 前置 后置 环绕?
	 */
	@Override
	public String filterType() {
		return POST_TYPE;
	}

	/**
	 * 执行顺序
	 */
	@Override
	public int filterOrder() {
		return 6;
	}

}
