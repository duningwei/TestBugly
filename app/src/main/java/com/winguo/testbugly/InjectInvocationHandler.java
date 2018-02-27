package com.winguo.testbugly;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/7/28.
 */

public class InjectInvocationHandler implements InvocationHandler {
    //    拦截的方法名列表
    private Map<String, Method> map = new HashMap<>();
    //    在这里实际上是MainActivity
    private Object target;

    public InjectInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (target != null) {
//            获取方法名
            String name = method.getName();
            Method m = map.get(name);
            if (m != null) {//如果不存在与拦截列表，就执行
                return m.invoke(target, args);
            }
        }
        return null;
    }

    /**
     * 向拦截列表里添加拦截的方法
     */
    public void add(String name, Method method) {
        map.put(name, method);
    }
}
