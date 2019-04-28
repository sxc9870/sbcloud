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
@Component
public class MyZuulPreFilter extends ZuulFilter {
	private MyZuulConfig yyZuulConfig;
	public MyZuulPreFilter(MyZuulConfig m) {
		this.yyZuulConfig=m;
	}
	/**
	 * 是否生效
	 */
	@Override
	public boolean shouldFilter() {
		RequestContext ctx=RequestContext.getCurrentContext();
		return  yyZuulConfig.getNeedLoginRoutes().contains(ctx.get(PROXY_KEY));
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx=RequestContext.getCurrentContext();
		HttpServletRequest req=ctx.getRequest();
		String name=req.getParameter("name");
		if (!name.equals("test")) {
			ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
			
			
			//写法1:  这种写法会导致接下来的pre拦截器不执行,并且直接调用post拦截器 不调用route拦截器
		   throw new ZuulException("权限不足",HttpStatus.FORBIDDEN.value(), "权限不足");
			
			//写法2:基本上和上面那个一样,只不过会这个版本好像有点问题 会包裹成UndeclaredThrowableException
		  //  ReflectionUtils.rethrowRuntimeException(new ZuulException("权限不足",HttpStatus.FORBIDDEN.value(), name));
		    //写法3:这种设置方法会导致 ribbon不去请求外部链接,但是还是会走余下的所有filter,并且不会进入error拦截器
//			 ctx.setSendZuulResponse(false);//
//			JSONObject json=new JSONObject();
//			try {
//				json.put("message", "没权限");
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		//	ctx.getResponse().setContentType("text/html;charset=UTF-8");
//			 ctx.setResponseBody(json.toString());
//			 ctx.set("isSuccess",false);
//			 return null;
		}
		return null;
	}
	
	/**
	 * 定义拦截器类型 pre,post,route 前置 后置 环绕?
	 */
	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	/**
	 * 执行顺序
	 */
	@Override
	public int filterOrder() {
		return 6;
	}

}
