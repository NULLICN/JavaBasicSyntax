package xyz.nullicn;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Calculator 计算器测试")
class CalculatorTest {

    private Calculator calculator;

    // 所有测试方法执行前只运行一次（必须是 static）
    @BeforeAll
    static void initAll() {
        System.out.println("== @BeforeAll：整个测试类只执行一次 ==");
    }

    // 每个测试方法执行前运行（常用：初始化被测对象）
    @BeforeEach
    void init() {
        calculator = new Calculator();
        System.out.println("-- @BeforeEach：每个测试方法前执行 --");
    }

    // 每个测试方法执行后运行（常用：清理资源）
    @AfterEach
    void tearDown() {
        System.out.println("-- @AfterEach：每个测试方法后执行 --");
    }

    // 所有测试方法执行完后只运行一次（必须是 static）
    @AfterAll
    static void cleanUpAll() {
        System.out.println("== @AfterAll：整个测试类只执行一次 ==");
    }

    @Test
    @DisplayName("加法：1 + 2 应等于 3")
    void add_returnsSum() {
        assertEquals(3, calculator.add(1, 2), "1 + 2 应该等于 3");
    }

    @Test
    @DisplayName("除法：除以 0 应抛出 IllegalArgumentException")
    void divide_byZero_throws() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> calculator.divide(10, 0));
        assertEquals("除数不能为 0", ex.getMessage());
    }

    @Test
    @DisplayName("组合断言：assertAll 一次性校验多个结果")
    void multiple_assertions() {
        assertAll("批量校验",
                () -> assertEquals(4, calculator.add(2, 2)),
                () -> assertEquals(6, calculator.subtract(10, 4)),
                () -> assertTrue(calculator.isPositive(5))
        );
    }

    // 参数化测试：同一个测试方法跑多组数据
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 100})
    @DisplayName("参数化：正数判断（@ValueSource）")
    void isPositive_shouldBeTrue(int n) {
        assertTrue(calculator.isPositive(n));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2, 3",
            "-1, -1, -2",
            "0, 5, 5"
    })
    @DisplayName("参数化：加法（@CsvSource 多列输入）")
    void add_parameterized(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b));
    }

    @Test
    @Disabled("示例：暂时跳过的测试，比如还没实现的功能")
    @DisplayName("被 @Disabled 跳过的测试")
    void skippedTest() {
        assertEquals(999, calculator.add(1, 1));
    }

    // 嵌套测试：按场景分组，便于组织测试
    @Nested
    @DisplayName("减法相关测试")
    class SubtractTests {

        @Test
        @DisplayName("10 - 4 应等于 6")
        void subtract_positive() {
            assertEquals(6, calculator.subtract(10, 4));
        }

        @Test
        @DisplayName("5 - 5 应等于 0")
        void subtract_equal() {
            assertEquals(0, calculator.subtract(5, 5));
        }
    }
}
