package com.earth.message.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.util.logging.Logger;

@Slf4j
@Configuration
public class ElasticSearchConfig {
    @Bean
    public TransportClient client() {
        log.info("初始化开始中...");
        TransportClient client = null;
        try {
            TransportAddress transportAddress = new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300);
            // 配置信息
            Settings esSetting = Settings.builder()
                    .put("cluster.name","elasticsearch_zhuoli")
                    .build();
            // 配置信息Settings自定义
            client= new PreBuiltTransportClient(esSetting);
            client.addTransportAddresses(transportAddress);
        } catch (Exception e) {
            log.error("elasticsearch TransportClient create error!!!", e);
        }
        return client;
    }
}
