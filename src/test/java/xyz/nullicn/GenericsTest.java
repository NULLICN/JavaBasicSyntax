package xyz.nullicn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@DisplayName("泛型测试")
public class GenericsTest {
    private <T> void customClassPrint(T t) {
        System.out.println(t.getClass());
    }

    @Test
    @DisplayName("自定义泛型方法测试")
    public void testUser() {
        User user = new User();
        customClassPrint(user);
    }

    private <T extends Account> void boundFunc(T t) {
        System.out.println(t.getClass());
    }

    @Test
    @DisplayName("泛型类的上界测试")
    public void testClassBound() {
        User user = new User();
        NoExtendsAccount noExtendsAccount = new NoExtendsAccount();
        boundFunc(user);
    }

    // 上边界 表明被操作的类至少是Account本身及子类，用于取出操作
    private <T extends Account & CommonFunction<T>> void testExtendsAndImpl(T t) {
        t.commonFunction(t);
    }

    @Test
    @DisplayName("泛型类上界与接口实现测试")
    public void testClassExtendsAndImpl() {
        User user = new User();
        testExtendsAndImpl(user);
    }

    // 下边界 允许T以及T的父类(此处已显示指定为Account 还可替换为T根据传入的类型动态设定下界)
    // 表明内部元素至少为指定类或Object
    private static void testSuper(List<? super Account> t) {
        t.add(new User());
        t.add(new Account());
    }

    @Test
    @DisplayName("泛型下界测试")
    public void testClassSuper() {
        List<User> users = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        List<Object> objects = new ArrayList<>();

        // testSuper(users); // 子类不可以放入
        testSuper(accounts);
        testSuper(objects);
        User user = new User();
        try {
            Class<User> userClass = User.class;
            Object o = userClass.getDeclaredConstructor().newInstance();
            Method m = userClass.getMethod("commonFunction", User.class);
            // m.isAnnotationPresent()
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}

class User extends Account implements CommonFunction<User> {
    private String name;

    public User() {}

    @Override
    public User commonFunction(User user) {
        System.out.println(user.getClass());
        return user;
    }
}

class NoExtendsAccount {
    private String className;
}

class Account {
    private String uid;
}

interface CommonFunction<T> {
    T commonFunction(T t);
}
