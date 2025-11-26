package com.example.generator.pojo.enums;

import com.example.generator.pojo.enums.standard.IOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.function.Supplier;

/**
 * <p>
 * Operation描述:计算
 * <p>
 * 包名称：com.example.generator.pojo.enums
 * 类名称：Operation
 * 全路径：com.example.generator.pojo.enums.Operation
 * 类描述：计算
 *
 * @author Administrator-YHL
 * @date 2024年01月05日 11:44
 */
@Getter
@AllArgsConstructor
public enum Operation implements IOperation {
    PLUS("+") {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    }, MINUS("-") {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    }, TIMES("*") {
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    }, DIVIDE("/") {
        @Override
        public double apply(double x, double y) {
            return x / y;
        }
    };
    /**
     * 计算
     */
    private final String code;

    /**
     * 判断传入的枚举是否存在当前枚举中
     *
     * @param code 编码值
     * @return 一个都没有匹配到返回 true
     */
    public static boolean enumNoneMatch(String code) {
        return Arrays.stream(values()).noneMatch(m -> code.equals(m.getCode()));
    }

    /**
     * 根据code 获取本身枚举
     *
     * @param code 编码值
     * @return 枚举
     */
    public static Operation findByCodeEnum(String code) throws Throwable {
        return Arrays.stream(values()).filter(r -> r.getCode().equals(code)).findFirst().orElseThrow((Supplier<Throwable>) () -> new NullPointerException("空指针"));
    }

    public static void main(String[] args) {
        double x = 4;
        double y = 2;
        for (Operation operation : Operation.values()) {
            System.out.printf("%f %s %f = %f%n",
                    x, operation, y, operation.apply(x, y));
        }
        EnumSet<AuthorityEnum> week = EnumSet.noneOf(AuthorityEnum.class);
        week.add(AuthorityEnum.READABLE);
        System.out.println("EnumSet中的元素：" + week);
        week.remove(AuthorityEnum.READABLE);
        System.out.println("EnumSet中的元素：" + week);
        week.addAll(EnumSet.complementOf(week));
        System.out.println("EnumSet中的元素：" + week);
        week.removeAll(EnumSet.range(AuthorityEnum.RUNNABLE, AuthorityEnum.READABLE));
        System.out.println("EnumSet中的元素：" + week);
    }
}
