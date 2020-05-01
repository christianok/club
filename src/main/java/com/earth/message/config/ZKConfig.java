package com.earth.message.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZKConfig {
    @Bean
    public CuratorFramework curatorFramework() {
        return CuratorFrameworkFactory.newClient("127.0.0.1:2181", new RetryNTimes(5, 1000));
    }
}
