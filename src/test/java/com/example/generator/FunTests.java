package com.example.generator;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.example.generator.pojo.dto.UserDTO;
import com.google.common.collect.Maps;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
        for (int i = 0; i < 3; i++) {
            Map<String, String> map = Maps.newHashMap();
            map.put("k", "k-".concat(String.valueOf(i)));
            map.put("j", "j-".concat(String.valueOf(i)));
            map.put("h", "h-".concat(String.valueOf(i)));
            list.add(map);
        }
        initFun(list, t -> t.get("j"));

        List<UserDTO> userDTOList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName("userName-".concat(String.valueOf(i)));
            userDTO.setUserId("userId-".concat(String.valueOf(i)));
            userDTOList.add(userDTO);
        }
        initFunUserDTO(userDTOList, UserDTO::getUserName);
        System.out.println("===============常用个函数=======================");
        testFun();
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

    /**
     * 常用的函数式接口
     * Supplier函数
     * Consumer函数
     * Predicate函数
     * Function函数
     */
    private static void testFun() {
        System.out.println("=====================Supplier函数==========================");
        String str = getString(() -> "Hello, World!");
        System.out.println(str);
        System.out.println("=====================Consumer函数==========================");
        operatorString("张三", System.out::println);
        operatorString("张三", System.out::println, (s) -> System.out.println(new StringBuilder(s).reverse()));
        System.out.println("=====================Predicate函数==========================");
        boolean string = chenkString("张三", s -> s.equals("张三"));
        System.out.println(string);
        boolean hello = chenkString("hello", s -> s.length() > 8, s -> s.length() < 18);
        System.out.println(hello);
        String[] strArray = {
                "张三,30", "李四,21", "王五,18"
        };
        ArrayList<String> arrayList = myFilter(strArray,
                s -> s.split(",")[0].length() > 1,
                s -> Integer.parseInt(s.split(",")[1]) > 20
        );
        arrayList.forEach(System.out::println);
        System.out.println("=====================Function函数==========================");
        convert("18", Integer::parseInt);
        convert(20, integer -> String.valueOf(integer + 18));
        convert("245", Integer::parseInt, integer -> String.valueOf(integer + 18));
    }

    /**
     * 定义一个方法，返回一个字符串数据
     * T get()：获得结果
     * 该方法不需要参数，他会按照某种实现逻辑（由Lambda表达式实现）返回一个数据
     * Supplier< T >接口也被称为生产型接口，如果我们指定了接口的泛型是什么类型，那么接口中的get方法就会产生什么类型的数据供我们使用
     *
     * @param sup
     * @return
     */
    private static String getString(Supplier<String> sup) {
        return sup.get();
    }

    /**
     * void accept(T t)：对给定的参数执行此操作
     * default Consumer < T > andThen(Consumer after)：返回一个组合的Consumer，依次执行此操作，然后执行after操作
     * Consumer< T >接口也被称为消费型接口，它消费的数据类型由泛型指定
     *
     * @param name
     * @param con
     */
    //定义一个方法，消费一个字符串数据
    private static void operatorString(String name, Consumer<String> con) {
        con.accept(name);
    }

    //定义一个方法，用不同的方式消费同一个字符串两次
    private static void operatorString(String name, Consumer<String> con1, Consumer<String> con2) {
        //con1.accept(name);
        //con2.accept(name);
        //返回一个组合的Consumer
        con1.andThen(con2).accept(name);
    }

    /**
     * Predicate
     * boolean test(T t)：对给定的参数进行判断（判断逻辑由Lambda表达式实现），返回一个布尔值
     * default Predicate< T > negate()：返回一个逻辑的否定，对应逻辑非
     * default Predicate< T > and()：返回一个组合判断，对应短路与
     * default Predicate< T > or()：返回一个组合判断，对应短路或
     * isEqual()：测试两个参数是否相等
     * Predicate< T >：接口通常用于判断参数是否满足指定的条件
     *
     * @param s
     * @param pre
     * @return
     */
    //判定给定的字符串是否满足要求
//    private static boolean chenkString(String s, Predicate<String> pre){
//        return pre.test(s);
//    }
    private static boolean chenkString(String s, Predicate<String> pre) {
        return pre.negate().test(s);
    }

//    private static boolean chenkString(String s, Predicate<String> pre, Predicate<String> pre1){
//        return pre.and(pre1).test(s);
//    }

    private static boolean chenkString(String s, Predicate<String> pre, Predicate<String> pre1) {
        return pre.or(pre1).test(s);
    }

    /**
     * 通过Predicate接口的拼装将符合要求的字符串筛选到集合Arraylist中
     *
     * @param strArray
     * @param pre1
     * @param pre2
     * @return
     */
    private static ArrayList<String> myFilter(String[] strArray, Predicate<String> pre1, Predicate<String> pre2) {
        //定义一个集合
        ArrayList<String> array = new ArrayList<>();
        //遍历数组
        for (String str : strArray) {
            if (pre1.and(pre2).test(str)) {
                array.add(str);
            }
        }
        return array;
    }

    /**
     * 定义一个方法，把一个字符串转换成int类型，在控制台输出
     * @param s
     * @param fun
     */
    private static void convert(String s, Function<String, Integer> fun) {
        int i = fun.apply(s);
        System.out.println(i);
    }

    /**
     * 定义一个方法，把int类型数据加上一个整数之后，转换为字符串在控制台输出
     * @param i
     * @param fun
     */
    private static void convert(int i, Function<Integer, String> fun) {
        String s = fun.apply(i);
        System.out.println(s);
    }

    /**
     * 定义一个方法，把一个字符串转换int类型，把int类型的数据加上一个整数后，转换成字符串在控制台输出
     * @param s
     * @param fun1
     * @param fun2
     */
    private static void convert(String s, Function<String, Integer> fun1, Function<Integer, String> fun2) {
        String s1 = fun2.apply(fun1.apply(s));
        System.out.println(s1);
    }
}
