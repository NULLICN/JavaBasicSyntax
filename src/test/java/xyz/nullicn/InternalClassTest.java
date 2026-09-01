package xyz.nullicn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("内部类测试")
public class InternalClassTest {
    // 内部类
    class InnerClass {
        public String name = "内部成员类";
    }

    // 静态内部类
    public static String name = "静态成员变量";
    public static class StaticInnerClass {
        public String name = "静态内部成员类，可以访问外层同样的静态成员";
    }

    // 局部定义类
    public void func() {
        class Fc {
            public String name = "局部定义内(函数内定义)";
        }
        System.out.println(new Fc().name);
    }

    // 匿名类
    interface AnonymousClass {
        void doSomething();
//        void doSth(); // 此处解开注释会导致lambuda匿名实现报错
    }
    public void doSthFun() {
        AnonymousClass anonymousClass = new AnonymousClass() {
            @Override
            public void doSomething() {
                System.out.println("匿名内部类 do something");
            }
        };
        anonymousClass.doSomething();
        AnonymousClass an = () -> System.out.println("lambda方式匿名类，但接口只能拥有一个抽象方法");
        an.doSomething();
    }

    @Test
    @DisplayName("测试匿名类")
    void testAnonymous() {
        doSthFun();
    }
}
