package com.example.generator;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.example.generator.pojo.dto.UserDTO;
import com.google.common.collect.Maps;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
        System.out.println("================调用函数======================");
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, String> map = Maps.newHashMap();
            map.put("k", "k-".concat(String.valueOf(i)));
            map.put("j", "j-".concat(String.valueOf(i)));
            map.put("h", "h-".concat(String.valueOf(i)));
            list.add(map);
        }
        initFun(list, t -> t.get("j"));

        List<UserDTO> userDTOList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName("userName-".concat(String.valueOf(i)));
            userDTO.setUserId("userId-".concat(String.valueOf(i)));
            userDTOList.add(userDTO);
        }
        initFunUserDTO(userDTOList, UserDTO::getUserName);
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

    /**
     * 函数传参
     *
     * @param list     原始数据
     * @param function 需要获取的函数
     */
    private static void initFun(List<Map<String, String>> list, Function<Map<String, String>, String> function) {
        list.parallelStream().forEach(r -> System.out.println(function.apply(r)));
    }

    /**
     * 初始化UserDTO列表，通过提供的函数对每个UserDTO进行处理，并打印处理结果。
     *
     * @param list     用户DTO列表，将对这个列表中的每个元素进行处理。
     * @param function 一个函数接口，用于对每个UserDTO对象进行处理并返回一个String结果。
     *                 这个函数的具体逻辑由调用方提供，这里只是简单地将其应用到每个列表元素并打印结果。
     */
    private static void initFunUserDTO(List<UserDTO> list, Function<UserDTO, String> function) {
        list.parallelStream().forEach(r -> System.out.println(function.apply(r)));
    }
}
