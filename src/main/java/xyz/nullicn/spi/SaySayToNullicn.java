package xyz.nullicn.spi;

import xyz.nullicn.SayToNullicn;

public class SaySayToNullicn implements SayToNullicn {
    // 用Hello实现
    @Override
    public void printHN() {
        System.out.println("Hello Nullicn!");
    }
}
