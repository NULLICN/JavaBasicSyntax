package xyz.nullicn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@DisplayName("BigDecimal测试")
public class BigDecimalTest {

    @Test
    @DisplayName("创建")
    public void createBig() {
        BigDecimal byStr = new BigDecimal("1");
        BigDecimal byValueof = BigDecimal.valueOf(0.1);

    }

    @Test
    @DisplayName("值比较方式")
    public void compareTest() {
        BigDecimal a = new BigDecimal("1.0");
        BigDecimal b = new BigDecimal("1.00");
        System.out.println(a.equals(b));     // false，精度不同
        System.out.println(a.compareTo(b));  // 0，值相等

    }
}
