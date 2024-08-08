package com.example.generator;

/**
 * <p>
 * PSTests描述:
 * <p>
 * 包名称：com.example.generator
 * 类名称：PSTests
 * 全路径：com.example.generator.PSTests
 * 类描述：
 *
 * @author Administrator-YHL
 * @date 2024年04月15日 10:59
 */
public class PSTests {
    public static void main(String[] args) {
        // 打印九九乘法表
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(i + "X" + j + "= " + i * j + "\t");
            }
            System.out.println();
        }
    }
}
