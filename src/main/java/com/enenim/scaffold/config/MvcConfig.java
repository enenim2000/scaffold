package com.enenim.scaffold.config;

import com.enenim.scaffold.interceptor.AuthenticationInterceptor;
import com.enenim.scaffold.interceptor.BaseInterceptor;
import com.enenim.scaffold.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
	private final AuthenticationInterceptor authenticationInterceptor;
	private final BaseInterceptor baseInterceptor;
	private final RequestInterceptor requestInterceptor;

	@Autowired
	public MvcConfig(AuthenticationInterceptor authenticationInterceptor, BaseInterceptor baseInterceptor, RequestInterceptor requestInterceptor) {
		this.authenticationInterceptor = authenticationInterceptor;
		this.baseInterceptor = baseInterceptor;
		this.requestInterceptor = requestInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(baseInterceptor).addPathPatterns("/**");
		registry.addInterceptor(authenticationInterceptor).addPathPatterns("/**");
		//registry.addInterceptor(requestInterceptor).addPathPatterns("/**");
	}
}
