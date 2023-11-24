package com.example.generator;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

/**
 * <p>
 * FunTests描述:函数测试
 * <p>
 * 包名称：com.example.generator
 * 类名称：FunTests
 * 全路径：com.example.generator.FunTests
 * 类描述：函数测试
 *
 * @author Administrator-YHL
 * @date 2023年11月24日 14:48
 */
public class FunTests {
    public static void main(String[] args) {
        System.out.println("======================================");
        try { //方式1: Lambda
            SFunction<String, String> lambda = String::toUpperCase;

            Method method = lambda.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(lambda);

            System.out.println("Lambda方式:");
            printInfo(serializedLambda);
        } catch (Exception e) {
            throw new RuntimeException("获取Lambda信息失败", e);
        }
        System.out.println("======================================");
    }

    private static void printInfo(SerializedLambda serializedLambda) {
        System.out.println("capturingClass: " + serializedLambda.getCapturingClass());
        System.out.println("functionalInterfaceClass: " + serializedLambda.getFunctionalInterfaceClass());
        System.out.println("functionalInterfaceMethodName: " + serializedLambda.getFunctionalInterfaceMethodName());
        System.out.println("functionalInterfaceMethodSignature: " + serializedLambda.getFunctionalInterfaceMethodSignature());
        System.out.println("implClass: " + serializedLambda.getImplClass());
        System.out.println("implMethodName: " + serializedLambda.getImplMethodName());
        System.out.println("implMethodSignature: " + serializedLambda.getImplMethodSignature());
        System.out.println("instantiatedMethodType: " + serializedLambda.getInstantiatedMethodType());
        System.out.println("implMethodKind: " + serializedLambda.getImplMethodKind());
    }
}
