package com.winguo.testbugly;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.winguo.testbugly.annotate.BaseEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by admin on 2017/7/28.
 */

public class AnnotateUtils {

    public static void injectViews(Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        if (declaredFields != null) {
            for (Field field : declaredFields) {
                ViewInject annotation = field.getAnnotation(ViewInject.class);
                if (annotation != null) {
                    int valueID = annotation.value();
                    if (valueID != 0) {
                        try {
                            Method findViewById = aClass.getMethod("findViewById", int.class);
                            Object resView = findViewById.invoke(activity, valueID);
                            field.setAccessible(true);
                            field.set(activity, resView);

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }

    }

    public static void OnClick(Activity activity) {
//        获取MainActivity
        Class<? extends Activity> clazz = activity.getClass();

//        获取MainActivity中所有方法
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {

            //           获取方法上对应的@IOnclick的注解
            Annotation[] annotations = method.getAnnotations();
            Log.e(AnnotateUtils.class.getSimpleName(),"OnClick："+methods.length+"::"+annotations.length);
            for (Annotation annotation : annotations) {

                //                通过annotationType获取注解@BaseEvent
                Class<? extends Annotation> annotationType = annotation.annotationType();

                //需要判断是否为null
                if (annotationType != null) {

//                    获取@IOnclick注解上的BaseEvent注解
                    BaseEvent baseEvent = annotationType.getAnnotation(BaseEvent.class);

                    //需要判断是否为null，因为有的注解没有@BaseEvent
                    if (baseEvent != null) {

//                        获取@BaseEvent的三个value
                        String callback = baseEvent.listenerCallback();
                        Class type = baseEvent.listenerType();
                        String setListener = baseEvent.setListener();

                        try {

//                            通过反射获取方法，@IOnclick里的int[] value()不需要传参，所以参数省略
                            Method declaredMethod = annotationType.getDeclaredMethod("value");

//                            调用方法，获取到@IOnclick的value，即两个button的id，参数省略
                            int[] valuesIds = (int[]) declaredMethod.invoke(annotation);

//                            这个类稍后会给出代码，目的是拦截方法
                            InjectInvocationHandler handler = new InjectInvocationHandler(activity);
//                            添加到拦截列表
                            handler.add(callback, method);

//                            得到监听的代理对象
                            Proxy proxy = (Proxy) Proxy.newProxyInstance(type.getClassLoader(),
                                    new Class[]{type}, handler);

//                            遍历所有button的id
                            for (int valuesId : valuesIds) {
                                View view = activity.findViewById(valuesId);
//                                通过反射获取方法
                                Method listener = view.getClass().getMethod(setListener, type);
//                                执行方法
                                listener.invoke(view, proxy);
                            }

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


}
