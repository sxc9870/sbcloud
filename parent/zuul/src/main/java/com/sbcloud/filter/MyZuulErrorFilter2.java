package com.sbcloud.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;
import com.sbcloud.MyZuulConfig;

@Component
public class MyZuulErrorFilter2 extends SendErrorFilter {
	private MyZuulConfig yyZuulConfig;

	public MyZuulErrorFilter2(MyZuulConfig m) {
		this.yyZuulConfig = m;
	}

	@Override
	public Object run() {
		System.out.println("error2");
		RequestContext ctx = RequestContext.getCurrentContext();
		ExceptionHolder exception = findZuulException(ctx.getThrowable());
		HttpServletRequest request = ctx.getRequest();
		JSONObject json=new JSONObject();
		try {
			json.put("code:", exception.getStatusCode());
			json.put("message:", exception.getErrorCause());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ctx.getResponse().setContentType("text/html;charset=UTF-8");
		ctx.setResponseBody(json.toString());
		ctx.remove("throwable");
        //super.run()
		return null;
	}

}
