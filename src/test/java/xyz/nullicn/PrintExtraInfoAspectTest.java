package xyz.nullicn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.nullicn.aspect.PrintExtraInfoAspect;
import xyz.nullicn.service.GreetingService;
import xyz.nullicn.service.GreetingServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 验证纯 JDK（无 Spring）的注解处理器：动态代理方式。
 */
@DisplayName("纯 JDK 注解处理器（动态代理）测试")
public class PrintExtraInfoAspectTest {

    @Test
    @DisplayName("被 @PrintExtraInfo 标注的方法会在调用前后打印额外信息")
    void testPrintExtraInfo() {
        // 用切面包装真实对象，得到代理对象
        GreetingService service = PrintExtraInfoAspect.wrap(
                new GreetingServiceImpl(),
                GreetingService.class
        );

        // 调用代理对象的方法，会自动触发切面打印，然后真正执行 greet
        String result = service.greet("张三");

        // 方法被正常执行，返回结果不受切面影响
        assertEquals("你好，张三！", result);
    }
}
