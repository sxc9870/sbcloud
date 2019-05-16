//package com.sbcloud;
//
//import java.lang.reflect.Method;
//import java.util.Set;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.annotation.AnnotatedElementUtils;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
//
//import de.codecentric.boot.admin.server.web.PathUtils;
//
//public class ZipkinControllerHandlerMapping extends RequestMappingHandlerMapping {
//
//	@Override
//	protected boolean isHandler(Class<?> beanType) {
//		return beanType.getSimpleName().contains("ZipkinUiAutoConfiguration");
//	}
//
//	@Override
//	protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
//		if (mapping.getPatternsCondition().getPatterns().contains("/")) {
//			super.registerHandlerMethod(handler, method, withPrefix(mapping));
//		} else
//			super.registerHandlerMethod(handler, method, mapping);
//	}
//
//	private RequestMappingInfo withPrefix(RequestMappingInfo mapping) {
////		if (!StringUtils.hasText("")) {
////			return mapping;
////		}
//		PatternsRequestCondition patternsCondition = new PatternsRequestCondition(
//				withNewPatterns(mapping.getPatternsCondition().getPatterns()));
//		return new RequestMappingInfo(patternsCondition, mapping.getMethodsCondition(), mapping.getParamsCondition(),
//				mapping.getHeadersCondition(), mapping.getConsumesCondition(), mapping.getProducesCondition(),
//				mapping.getCustomCondition());
//	}
//
//	private String[] withNewPatterns(Set<String> patterns) {
//		return patterns.stream().map(pattern -> PathUtils.normalizePath("/sxc/" + pattern)).toArray(String[]::new);
//	}
//}
