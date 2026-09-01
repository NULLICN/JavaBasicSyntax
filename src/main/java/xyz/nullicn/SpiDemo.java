package xyz.nullicn;

import java.util.ServiceLoader;

public class SpiDemo {
    public static void main(String[] args) {
        // 1. 获取ServiceLoader实例（指定接口类型）
        ServiceLoader<SayToNullicn> serviceLoader = ServiceLoader.load(SayToNullicn.class);
        // 2. 遍历所有加载到的实现类（延迟加载，遍历到才创建实例）
        // META-INF/services 中对应 "接口全限定名" 文件中的 "实现类的全限定名" 内容都会被加载
        for (SayToNullicn Hn : serviceLoader) {
            Hn.printHN();
        }
    }
}