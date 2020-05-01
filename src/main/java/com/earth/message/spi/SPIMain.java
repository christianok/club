package com.earth.message.spi;

import java.util.ServiceLoader;

public class SPIMain {
    public static void main(String[] args) {

        ServiceLoader<DatabaseInterface> loaders = ServiceLoader
                .load(DatabaseInterface.class);

        int i=0;

        for (DatabaseInterface in : loaders) {
            in.querySth();
            i++;
        }

        System.out.println();
        System.out.println("找到服务实现类："+i);
    }
}
