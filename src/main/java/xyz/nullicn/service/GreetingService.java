package xyz.nullicn.service;

/**
 * 演示用的业务接口。
 *
 * <p>JDK 动态代理必须基于接口，因此这里先定义一个接口，供代理实现。
 */
public interface GreetingService {

    /** 打招呼 */
    String greet(String name);
}
