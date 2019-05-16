/*
 * Copyright 2015-2018 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.sbcloud;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Zipkin-UI is a single-page application mounted at /zipkin. For simplicity,
 * assume paths mentioned below are relative to that. For example, the UI reads
 * config.json, from the absolute path /zipkin/config.json
 *
 * <p>
 * When looking at a trace, the browser is sent to the path "/traces/{id}". For
 * the single-page app to serve that route, the server needs to forward the
 * request to "/index.html". The same forwarding applies to "/dependencies" and
 * any other routes the UI controls.
 *
 * <p>
 * Under the scenes the JavaScript code looks at {@code window.location} to
 * figure out what the UI should do. This is handled by a route api defined in
 * the crossroads library.
 *
 * <h3>Caching</h3>
 * <p>
 * This includes a hard-coded cache policy, consistent with zipkin-scala.
 * <ul>
 * <li>1 minute for index.html</li>
 * <li>10 minute for /config.json</li>
 * <li>365 days for hashed resources (ex /app-e12b3bbb7e5a572f270d.min.js)</li>
 * </ul>
 * Since index.html links to hashed resource names, any change to it will orphan
 * old resources. That's why hashed resource age can be 365 days.
 */
@Configuration
@EnableConfigurationProperties(ZipkinUiProperties.class)
@RestController
public class ZipkinUiAutoConfiguration {
	@Autowired
	ZipkinUiProperties ui;

	@Value("${zipkin.ui.source-root:classpath:zipkin-ui}/index.html")
	Resource indexHtml;

	@Bean
	@Lazy
	String processedIndexHtml() throws IOException {
		String baseTagValue = "/".equals(ui.getBasepath()) ? "/" : ui.getBasepath() + "/";
		Document soup;
		try (InputStream is = indexHtml.getInputStream()) {
			soup = Jsoup.parse(is, null, baseTagValue);
		}
		if (soup.head().getElementsByTag("base").isEmpty()) {
			soup.head().appendChild(soup.createElement("base"));
		}
		soup.head().getElementsByTag("base").attr("href", baseTagValue);
		return soup.html();
	}

	@Bean
	public WebMvcConfigurer resourceConfigurer(
			@Value("${zipkin.ui.source-root:classpath:zipkin-ui}") String sourceRoot) {
		return new WebMvcConfigurer() {
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("/zipkin/**").addResourceLocations(sourceRoot + "/")
						.setCachePeriod((int) TimeUnit.DAYS.toSeconds(365));
			}
		};
	}

	@RequestMapping(value = "/zipkin/config.json", method = GET)
	public ResponseEntity<ZipkinUiProperties> serveUiConfig() {
		return ResponseEntity.ok().cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
				.contentType(MediaType.APPLICATION_JSON).body(ui);
	}

	@RequestMapping(value = "/zipkin/index.html", method = GET)
	public ResponseEntity<?> serveIndex() throws IOException {
		ResponseEntity.BodyBuilder result = ResponseEntity.ok().cacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES))
				.contentType(MediaType.TEXT_HTML);
		return ZipkinUiProperties.DEFAULT_BASEPATH.equals(ui.getBasepath()) ? result.body(indexHtml)
				: result.body(processedIndexHtml());
	}

//	@ModelAttribute(value = "baseUrl", binding = false)
//	public String getBaseUrl(UriComponentsBuilder uriBuilder) {
//		UriComponents publicComponents = UriComponentsBuilder.fromUriString(ui.getBasepath()).build();
//		if (publicComponents.getScheme() != null) {
//			uriBuilder.scheme(publicComponents.getScheme());
//		}
//		if (publicComponents.getHost() != null) {
//			uriBuilder.host(publicComponents.getHost());
//		}
//		if (publicComponents.getPort() != -1) {
//			uriBuilder.port(publicComponents.getPort());
//		}
//		if (publicComponents.getPath() != null) {
//			uriBuilder.path(publicComponents.getPath());
//		}
//		return uriBuilder.path("/").toUriString();
//	}

	/**
	 * This cherry-picks well-known routes the single-page app serves, and forwards
	 * to that as opposed to returning a 404.
	 */
	// TODO This approach requires maintenance when new UI routes are added. Change
	// to the following:
	// If the path is a a file w/an extension, treat normally.
	// Otherwise instead of returning 404, forward to the index.
	// See
	// https://github.com/twitter/finatra/blob/458c6b639c3afb4e29873d123125eeeb2b02e2cd/http/src/main/scala/com/twitter/finatra/http/response/ResponseBuilder.scala#L321
	@RequestMapping(value = { "/zipkin/", "/zipkin/traces/{id}", "/zipkin/dependency",
			"/zipkin/traceViewer" }, method = GET)
	public ModelAndView forwardUiEndpoints() {
		return new ModelAndView("forward:/zipkin/index.html");
	}

	/** The UI looks for the api relative to where it is mounted, under /zipkin */
	@RequestMapping(value = "/zipkin/api/**", method = GET)
	public ModelAndView forwardApi(HttpServletRequest request) {
		String path = (String) request.getAttribute(PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		return new ModelAndView("forward:" + path.replaceFirst("/zipkin", ""));
	}

	/** Borrow favicon from UI assets under /zipkin */
	@RequestMapping(value = "/favicon.ico", method = GET)
	public ModelAndView favicon() {
		return new ModelAndView("forward:/zipkin/favicon.ico");
	}

//  /** Make sure users who aren't familiar with /zipkin get to the right path */
//  @RequestMapping(value = "/", method = GET)
//  public void redirectRoot(HttpServletResponse response) {
//    // return 'Location: ./zipkin/' header (this wouldn't work with ModelAndView's 'redirect:./zipkin/')
//    response.setHeader(HttpHeaders.LOCATION, "./zipkin/");
//    response.setStatus(HttpStatus.FOUND.value());
//  }
}
