package com.opensource.component.logs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableConfigurationProperties({ReqLogProperties.class})
@Configuration
@ConditionalOnProperty(
        value = {"component.req.logs.enabled"},
        matchIfMissing = true
)
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sysLogInterceptor());
    }

    @Bean
    public SysLogInterceptor sysLogInterceptor() {
        return new SysLogInterceptor();
    }

}
