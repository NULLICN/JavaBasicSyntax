package xyz.nullicn.aspect;

import xyz.nullicn.annotation.PrintExtraInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 不依赖 Spring，纯 JDK 实现的"注解处理器"（切面）。
 *
 * <p>核心思路：利用 JDK 动态代理 {@link Proxy}，在真实目标对象外面包一层代理。
 * 外界调用代理对象的方法时，会先进入 {@link #invoke}，在这里通过反射读取
 * {@link PrintExtraInfo} 注解，从而在真正执行方法的前后打印额外信息，达到类似 AOP 的效果。
 *
 * <p>注意：JDK 动态代理要求被代理的目标类必须实现至少一个接口。
 * （这也是 Spring AOP 处理接口 Bean 的默认方式；如果只有纯类，则需要 CGLIB / ByteBuddy 做子类代理。）
 */
public class PrintExtraInfoAspect implements InvocationHandler {

    /** 被代理的真实目标对象 */
    private final Object target;

    private PrintExtraInfoAspect(Object target) {
        this.target = target;
    }

    /**
     * 工厂方法：为目标对象生成一个带切面逻辑的代理对象。
     *
     * @param target        真实目标对象（必须实现了 interfaceType 接口）
     * @param interfaceType 目标对象实现的接口类型，生成的代理也会实现该接口
     * @return 包了切面逻辑的代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T wrap(Object target, Class<T> interfaceType) {
        return (T) Proxy.newProxyInstance(
                interfaceType.getClassLoader(),          // 类加载器
                new Class<?>[]{interfaceType},           // 代理要实现的接口
                new PrintExtraInfoAspect(target)         // 方法调用处理器
        );
    }

    /**
     * 代理对象上的任意方法被调用时，都会进入这里。
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 1. 定位注解：优先取实现类上的真实方法，取不到再退回接口方法，
        //    这样无论注解标在实现类还是接口上都能识别。
        Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
        PrintExtraInfo annotation = targetMethod.getAnnotation(PrintExtraInfo.class);
        if (annotation == null) {
            annotation = method.getAnnotation(PrintExtraInfo.class);
        }

        // 2. 前置逻辑：带注解才打印"进入"信息
        if (annotation != null) {
            String extra = annotation.value();
            System.out.println("【PrintExtraInfo】进入方法 " + method.getName()
                    + (extra.isEmpty() ? "" : "，附加信息：" + extra));
        }

        // 3. 真正执行目标方法
        Object result = method.invoke(target, args);

        // 4. 后置逻辑：带注解才打印"离开"信息
        if (annotation != null) {
            System.out.println("【PrintExtraInfo】离开方法 " + method.getName() + "，返回：" + result);
        }

        return result;
    }
}
