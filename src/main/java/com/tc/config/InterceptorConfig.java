package com.tc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tc.core.interceptor.AdminAuthenticationInterceptor;
import com.tc.core.interceptor.UserAuthenticationInterceptor;
 

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminAuthenticationInterceptor()).addPathPatterns("/sys/**");

   //     registry.addInterceptor(new UserAuthenticationInterceptor()).addPathPatterns("/main/**");
        
        registry.addInterceptor(new UserAuthenticationInterceptor()).addPathPatterns("/applet/**");
    }
    
}
