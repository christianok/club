package com.earth.message.config;

import com.earth.message.thread.MyRunnable;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

@Configuration
public class AppConfig {

    @Bean
    MyRunnable myRunnable() {
        return new MyRunnable("");
    }

    @Bean
    MultipartConfigElement createMultipartConfigElement()
    {
        MultipartConfigFactory mcf = new MultipartConfigFactory();
        mcf.setMaxFileSize("50MB");
        return mcf.createMultipartConfig();
    }
}
