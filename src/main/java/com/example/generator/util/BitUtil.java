package com.example.generator.util;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson2.JSON;
import com.example.generator.pojo.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 * BitUtil描述:进制转换
 * <p>
 * 包名称：com.example.generator.util
 * 类名称：BitUtil
 * 全路径：com.example.generator.util.BitUtil
 * 类描述：进制转换
 *
 * @author Administrator-YHL
 * @date 2023年11月14日 16:02
 */
@Component
public class BitUtil {
    /**
     * 16进制字符串转10进制
     *
     * @param hex
     * @return
     */
    public static Integer hexStringToDecimal(String hex) {
        return Integer.valueOf(hex, 16);
    }

    /**
     * 16进制字符串转2进制字符串
     *
     * @param hex
     * @return
     */
    public static String hexStringToBinaryString(String hex) {
        Integer temp = Integer.valueOf(hex, 16);
        return Integer.toBinaryString(temp);
    }

    /**
     * 10进制转16进制字符串
     *
     * @param decimal
     * @return
     */
    public static String decimalToHexString(Integer decimal) {
        return Integer.toHexString(decimal);
    }

    /**
     * 10进制转2进制字符串
     *
     * @param decimal
     * @return
     */
    public static String decimalToBinaryString(Integer decimal) {
        return Integer.toBinaryString(decimal);
    }

    /**
     * 2进制字符串转10进制
     *
     * @param binary
     * @return
     */
    public static Integer binaryStringToDecimal(String binary) {
        return Integer.valueOf(binary, 2);
    }

    /**
     * 2进制字符串转16进制字符串
     *
     * @param binary
     * @return
     */
    public static String binaryStringToHexString(String binary) {
        Integer temp = Integer.valueOf(binary, 2);
        return Integer.toHexString(temp);
    }

    /**
     * byte[]转字符串
     *
     * @param bytes
     * @return
     */
    public static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        String temp = null;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xff);
            if (temp.length() == 1) {
                // 得到的一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    /**
     * byte[]转String[]
     *
     * @param bytes
     * @return
     */
    public static String[] bytesToStrings(byte[] bytes) {
        String[] strings = new String[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            String temp = Integer.toHexString(bytes[i] & 0xff);
            if (temp.length() == 1) {
                strings[i] = '0' + temp;
            } else {
                strings[i] = temp;
            }
        }
        return strings;
    }

    public static void main(String[] args) {
        UserDTO userDTO = new UserDTO();
        ReflectUtil.invoke(userDTO, "setUserId", "10");
        System.out.println(JSON.toJSONString(userDTO));
    }
}
