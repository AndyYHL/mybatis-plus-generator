package com.example.generator;

import cn.hutool.core.lang.func.LambdaUtil;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.support.ColumnCache;
import com.example.generator.pojo.domain.AccountDO;
import com.example.generator.pojo.domain.UserDO;
import com.example.generator.pojo.enums.AuthorityEnum;
import com.example.generator.util.AuthToolsUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
class MybatisPlusGeneratorApplicationTests {
    @Autowired
    private AuthToolsUtil authTools;

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        LocalDate rTime = LocalDate.ofInstant(Instant.ofEpochMilli(1698681600000l), ZoneId.systemDefault());
        System.out.println(rTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate date = LocalDate.now();
        LocalDateTime dd = LocalDateTime.parse(LocalDateTime.of(date, LocalTime.MIN).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(dd);
        LocalDateTime dateTime = LocalDateTime.parse("2023-10-31 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(dateTime);

        var jjj = "asdsfadsfddd";
        System.out.println(jjj.charAt(3));

        int bit = (int) Math.pow(2, 4);
        String byes = Integer.toBinaryString(bit);
        System.out.println("十进制转二进制:" + byes);

        Integer it = Integer.valueOf(byes, 2);
        System.out.println("转换为10进制结果:" + it);
    }

    UserDO user1 = UserDO.builder().userId("用户一").userName("用户一").auth(0B000).build();
    UserDO user2 = UserDO.builder().userId("用户二").userName("用户二").auth(0B000).build();

    /**
     * 给user1赋予Read权限
     */
    @Test
    public void addUser1Read() {
        System.out.println("赋值前：" + user1);
        authTools.addUserAuth(user1, AuthorityEnum.READABLE);
        System.out.println("赋值后：" + user1);

    }

    /**
     * 给user2赋予Write和Runnable权限
     */
    @Test
    public void addUser2Write() {
        System.out.println("赋值前：" + user2);
        authTools.addUserAuth(user2, AuthorityEnum.WRITABLE, AuthorityEnum.RUNNABLE);
        System.out.println("赋值后：" + user2);
    }

    /**
     * 删除use1的可运行权限
     */
    @Test
    public void delUser1Write() {
        // 先给予全部权限
        authTools.addUserAuth(user1, AuthorityEnum.READABLE, AuthorityEnum.WRITABLE, AuthorityEnum.RUNNABLE, AuthorityEnum.READABLE_WRITABLE);
        System.out.println("user1：" + user1);
        authTools.delUserAuth(user1, AuthorityEnum.RUNNABLE);
        System.out.println("删除可运行权限后user1：" + user1);
        authTools.execOpera(user1, AuthorityEnum.RUNNABLE);
        authTools.execOpera(user1, AuthorityEnum.READABLE);
        authTools.execOpera(user1, AuthorityEnum.READABLE_WRITABLE);
    }

    /**
     * 测试file的运行的情况
     */
    @Test
    public void execFileByUser() {
        authTools.addUserAuth(user1, AuthorityEnum.READABLE);
        System.out.println("user1：" + user1);
        System.out.println("user2：" + user2);
        authTools.execOpera(user1, AuthorityEnum.READABLE);
        authTools.execOpera(user1, AuthorityEnum.RUNNABLE);
        authTools.execOpera(user2, AuthorityEnum.READABLE);
    }

    /**
     * 获取数据库字段
     */
    @Test
    public void excTest() {
        //获取实体所有对应的列
        Map<String, ColumnCache> testMap = LambdaUtils.getColumnMap(AccountDO.class);
        //hutool包，获取实体属性名称
        String fieldName = LambdaUtil.getFieldName(AccountDO::getAccountName);
        String methodName = LambdaUtil.getMethodName(AccountDO::getAccountNo);
        System.out.println(fieldName);
        System.out.println(methodName);
        //通过属性名获取对应的列名
        if (Objects.nonNull(testMap)) {
            String column = testMap.get(LambdaUtils.formatKey(fieldName)).getColumn();
            System.out.println(column);
        }
    }
}
