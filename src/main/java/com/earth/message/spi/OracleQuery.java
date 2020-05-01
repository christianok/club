package com.earth.message.spi;

public class OracleQuery implements DatabaseInterface {
    @Override
    public void querySth() {
        System.out.println("oracle database query something!");
    }
}
