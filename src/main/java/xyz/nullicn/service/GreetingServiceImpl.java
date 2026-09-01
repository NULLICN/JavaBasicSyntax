package xyz.nullicn.service;

import xyz.nullicn.annotation.PrintExtraInfo;

/**
 * {@link GreetingService} 的实现类。
 *
 * <p>在方法上标注 {@link PrintExtraInfo}，表示执行该方法时希望打印额外信息。
 */
public class GreetingServiceImpl implements GreetingService {

    @PrintExtraInfo("问候服务")
    @Override
    public String greet(String name) {
        return "你好，" + name + "！";
    }
}
