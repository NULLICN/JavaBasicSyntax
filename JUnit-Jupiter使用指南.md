# JUnit Jupiter 使用指南

## 概述

JUnit 5 由三部分组成：`JUnit Platform`（测试运行基础）、`JUnit Jupiter`（编程模型与扩展 API）、`JUnit Vintage`（兼容 JUnit 3/4）。本指南介绍 `JUnit Jupiter` 的常用功能。

依赖引入（`pom.xml`）：

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.11.4</version>
    <scope>test</scope>
</dependency>
```

### 一、生命周期注解

##### `@BeforeAll`

在**整个测试类**所有方法执行前运行一次，必须声明为 `static`。

##### `@BeforeEach`

在**每个**测试方法执行前运行，常用于初始化被测对象。

##### `@AfterEach`

在**每个**测试方法执行后运行，常用于清理资源。

##### `@AfterAll`

在**整个测试类**所有方法执行后运行一次，必须声明为 `static`。

代码示例：

```java
@BeforeAll
static void initAll() {
    // 整个测试类只执行一次
}

@BeforeEach
void init() {
    calculator = new Calculator();
}

@AfterEach
void tearDown() {
    // 每个测试方法后执行
}

@AfterAll
static void cleanUpAll() {
    // 整个测试类只执行一次
}
```

### 二、核心注解

##### `@Test`

标记普通测试方法。注意包路径是 `org.junit.jupiter.api`，不是 JUnit 4 的 `org.junit`。

##### `@DisplayName("...")`

给测试起可读名称，测试报告中更友好。

##### `@Disabled("原因")`

跳过某个测试，常用于暂时未实现的功能。

##### `@Nested`

嵌套测试类，按场景分组，便于组织测试。

代码示例：

```java
@Test
@DisplayName("加法：1 + 2 应等于 3")
void add_returnsSum() {
    assertEquals(3, calculator.add(1, 2));
}

@Test
@Disabled("示例：暂时跳过的测试")
void skippedTest() {
    assertEquals(999, calculator.add(1, 1));
}

@Nested
@DisplayName("减法相关测试")
class SubtractTests {
    @Test
    void subtract_positive() {
        assertEquals(6, calculator.subtract(10, 4));
    }
}
```

### 三、断言（Assertions）

##### `assertEquals(expected, actual)`

断言两个值相等，可加第三个参数作为失败提示信息。

##### `assertTrue(condition)` / `assertFalse(condition)`

断言布尔条件为真 / 假。

##### `assertNull(obj)` / `assertNotNull(obj)`

断言对象为空 / 非空。

##### `assertThrows(异常类型.class, lambda)`

断言抛出指定异常，并返回异常对象，可进一步校验 `message`。

##### `assertAll("名称", () -> ..., ...)`

组合多个断言，一次性全部执行并汇总所有失败，不会在第一个失败处中断。

代码示例：

```java
assertEquals(3, calculator.add(1, 2), "1 + 2 应该等于 3");

assertTrue(calculator.isPositive(5));

assertNull(result);

IllegalArgumentException ex =
        assertThrows(IllegalArgumentException.class, () -> calculator.divide(10, 0));
assertEquals("除数不能为 0", ex.getMessage());

assertAll("批量校验",
        () -> assertEquals(4, calculator.add(2, 2)),
        () -> assertEquals(6, calculator.subtract(10, 4)),
        () -> assertTrue(calculator.isPositive(5))
);
```

### 四、参数化测试

##### `@ParameterizedTest`

让同一个测试方法跑多组数据，需配合数据源注解使用。

##### `@ValueSource(...)`

单参数多值数据源，如 `@ValueSource(ints = {1, 2, 3})`。

##### `@CsvSource(...)`

多列输入数据源，逗号分隔映射到方法参数。

##### 其他数据源

`@EnumSource`、`@MethodSource`、`@CsvFileSource`。

代码示例：

```java
@ParameterizedTest
@ValueSource(ints = {1, 2, 3, 100})
void isPositive_shouldBeTrue(int n) {
    assertTrue(calculator.isPositive(n));
}

@ParameterizedTest
@CsvSource({
        "1, 2, 3",
        "-1, -1, -2",
        "0, 5, 5"
})
void add_parameterized(int a, int b, int expected) {
    assertEquals(expected, calculator.add(a, b));
}
```

### 五、其他常用

##### `@RepeatedTest(n)`

重复执行测试 `n` 次。

##### `@Timeout(...)`

限制测试执行时间，超时判失败。

##### `@Tag("...")`

给测试打标签，配合 Maven 按标签过滤运行。

### 六、Maven 配置

##### 依赖

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.11.4</version>
    <scope>test</scope>
</dependency>
```

##### Surefire 插件

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.5.2</version>
</plugin>
```

> 说明：`junit-jupiter` 是聚合工件，已包含 API、参数化测试（`junit-jupiter-params`）和测试引擎（`junit-jupiter-engine`）。Surefire 插件需 `3.x` 版本才能正确识别并运行 JUnit 5 测试。
