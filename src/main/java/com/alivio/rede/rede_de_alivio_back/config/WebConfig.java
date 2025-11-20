package com.alivio.rede.rede_de_alivio_back.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<SecurityFilter> loggingFilter() {
        FilterRegistrationBean<SecurityFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SecurityFilter());
        registrationBean.addUrlPatterns("/api/relatos/*", "/api/comentarios/*");
        return registrationBean;
    }
}