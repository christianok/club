package com.earth.message.crontab;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class StartService implements ApplicationRunner {

    @Autowired
    private CuratorFramework curatorFramework;

    @Override
    public void run(ApplicationArguments applicationArguments) {
		// 非常重要！！！Start the client. Most mutator methods will not work until the client is started
        curatorFramework.start();
        System.out.println("zookeeper client start");

    }
}
