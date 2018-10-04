package com.enenim.scaffold.config;

import com.enenim.scaffold.constant.CommonConstant;
import com.enenim.scaffold.interceptor.AuthenticationInterceptor;
import com.enenim.scaffold.interceptor.BaseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	private final AuthenticationInterceptor authenticationInterceptor;
	private final BaseInterceptor baseInterceptor;

	@Autowired
	public MvcConfig(AuthenticationInterceptor authenticationInterceptor, BaseInterceptor baseInterceptor) {
		this.authenticationInterceptor = authenticationInterceptor;
		this.baseInterceptor = baseInterceptor;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String myExternalFilePath = "file:" + File.separator + File.separator + File.separator + CommonConstant.ASSET_BASE;
		registry.addResourceHandler("/assets/**").addResourceLocations(myExternalFilePath);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(baseInterceptor).addPathPatterns("/**");
		registry.addInterceptor(authenticationInterceptor).addPathPatterns("/**");
	}
}
