package com.sbcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class HelloWorldController {
	@Value("${cloud}")
	private String cloud;
    @RequestMapping("/hello")
    public String index() {
        return cloud;
    }
}