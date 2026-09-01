package xyz.nullicn.spi;

import xyz.nullicn.SayToNullicn;

public class SayHiNullicn implements SayToNullicn {
    // 用Hi实现
    @Override
    public void printHN() {
        System.out.println("Hi Nullicn!");
    }
}
