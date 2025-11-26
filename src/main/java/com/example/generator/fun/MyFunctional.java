package com.example.generator.fun;

import java.io.Serializable;

/**
 * <p>
 * MyFunctional描述:函数 该注解表示这是一个函数式接口：即接口中有且只能一个函数声明。
 * 注意：一定要继承序列化`Serializable`接口，后面会分析原因。
 * <p>
 * 包名称：com.example.generator.fun
 * 类名称：MyFunctional
 * 全路径：com.example.generator.fun.MyFunctional
 * 类描述：函数
 *
 * @author Administrator-YHL
 * @date 2023年11月24日 14:33
 */
@FunctionalInterface
public interface MyFunctional<T> extends Serializable {
    Object apply(T source);
}
