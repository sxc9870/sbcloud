package com.sbcloud.filter;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sbcloud.MyZuulConfig;

import brave.Tracer;
@Component
public class MyZuulPostFilter extends ZuulFilter {
	private MyZuulConfig yyZuulConfig;
	
	
	@Autowired
	private Tracer tracer;
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
		
		tracer.currentSpan().tag("sxc", "vvv");
		
		System.out.println(tracer.currentSpan().toString());
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
		return 900;
	}

}
