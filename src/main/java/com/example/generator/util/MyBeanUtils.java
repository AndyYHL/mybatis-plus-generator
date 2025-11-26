package com.example.generator.util;

import com.example.generator.fun.MyFunctional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <p>
 * MyBeanUtils描述:拷贝对象
 * <p>
 * 包名称：com.example.generator.util
 * 类名称：MyBeanUtils
 * 全路径：com.example.generator.util.MyBeanUtils
 * 类描述：拷贝对象
 *
 * @author Administrator-YHL
 * @date 2023年11月24日 14:32
 */
@Component
public class MyBeanUtils {
    //模仿BeanUtils.copyProperties()写一个自己的copyProperties方法，唯一的区别就是最后一个参数的类型由String改为MyFunctional
    public <T> void copyProperties(Object source, Object target, MyFunctional<T>... ignoreProperties) {
        String[] ignorePropertieNames = null;
        if (ignoreProperties != null && ignoreProperties.length > 0) {
            ignorePropertieNames = new String[ignoreProperties.length];
            for (int i = 0; i < ignoreProperties.length; i++) {
                MyFunctional<T> lambda = ignoreProperties[i];
                //根据lambda表达式得到字段名
                ignorePropertieNames[i] = getPropertyName(lambda);
            }
        }
        //最终还是调用Spring的工具类：
        BeanUtils.copyProperties(source, target, ignorePropertieNames);
    }

    //获取lamba表达式中调用方法对应的属性名，比如lamba表达式：User::getSex，则返回字符串"sex"
    private <T> String getPropertyName(MyFunctional<T> lambda) {
        try {
            //writeReplace从哪里来的？后面会讲到
            Method method = lambda.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            //调用writeReplace()方法，返回一个SerializedLambda对象
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(lambda);
            //得到lambda表达式中调用的方法名，如 "User::getSex"，则得到的是"getSex"
            String getterMethod = serializedLambda.getImplMethodName();
            //去掉”get"前缀，最终得到字段名“sex"
            return Introspector.decapitalize(getterMethod.replace("get", ""));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> String getPropertyName1(MyFunctional<T> lambda) {
        try {
            Class lambdaClass = lambda.getClass();
            System.out.println("-------------分割线1-----------");
            //打印类名：
            System.out.print("类名：");
            System.out.println(lambdaClass.getName());
            //打印接口名：
            System.out.print("接口名：");
            Arrays.stream(lambdaClass.getInterfaces()).forEach(System.out::print);
            System.out.println();
            //打印方法名：
            System.out.print("方法名：");
            for (Method method : lambdaClass.getDeclaredMethods()) {
                System.out.print(method.getName() + "  ");
            }

            System.out.println();
            System.out.println("-------------分割线2-----------");
            System.out.println();

            Method method = lambdaClass.getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(lambda);
            String getterMethod = serializedLambda.getImplMethodName();
            System.out.println("lambda表达式调用的方法名：" + getterMethod);
            String fieldName = Introspector.decapitalize(getterMethod.replace("get", ""));
            System.out.println("根据方法名得到的字段名：" + fieldName);

            System.out.println();
            System.out.println("-------------分割线3-----------");
            System.out.println();
            System.out.println("SerializedLambda中的所有方法：");
            for (Method declaredMethod : serializedLambda.getClass().getDeclaredMethods()) {
                if (declaredMethod.getParameterCount() == 0) {
                    declaredMethod.setAccessible(Boolean.TRUE);
                    System.out.println("调用方法： " + declaredMethod.getName() + ": " + declaredMethod.invoke(serializedLambda));
                } else {
                    System.out.println("方法声明：" + declaredMethod.getName() + "(" + Arrays.stream(declaredMethod.getParameterTypes()).map(Class::getName).collect(Collectors.joining(", ")) + ")");
                }

            }
            return fieldName;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
