package com.earth.message.spi;

public class MysqlQuery implements DatabaseInterface {
    @Override
    public void querySth() {
        System.out.println("mysql database query something!");
    }

}
