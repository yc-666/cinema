package com.zrkworld.cinema.config.cors_filter;

import com.thetransactioncompany.cors.CORSFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TXG
 * @date 2022/8/7$
 */
@Configuration
public class CorsConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean reg = new FilterRegistrationBean();
        //使用CORSFilter解决跨域请求
        reg.setFilter(new CORSFilter());
        //获取过滤器地址
        reg.addUrlPatterns("/*");
        return reg;
    }
}
