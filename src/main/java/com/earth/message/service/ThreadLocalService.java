package com.earth.message.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ThreadLocalService implements Runnable{
    ThreadLocal<Map<String, String>> threadLocal = ThreadLocal.withInitial(() -> {
        Map<String, String> map =  new HashMap<>();
        map.put("zhuo", "li");
        map.put("zhang", "ping");
        return map;
    });

    @Override
    public void run() {
        Map<String, String> map =  new HashMap<>();
        map.put("zhang", Thread.currentThread().getName());
        threadLocal.set(map);
        Map<String, String> stringMap = threadLocal.get();

        log.info("threadlocal map is {}", stringMap);
    }

    public static void main(String args[]) {
        ThreadLocalService threadLocalService = new ThreadLocalService();
        for (int i = 0; i < 10; i++) {
             Thread t = new Thread(threadLocalService);
             t.start();
        }
    }
}
