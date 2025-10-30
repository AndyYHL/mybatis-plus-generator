package com.example.generator.util;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.Locale;

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
        /*UserDTO userDTO = new UserDTO();
        ReflectUtil.invoke(userDTO, "setUserId", "10");
        System.out.println(JSON.toJSONString(userDTO));*/
        /*StringJoiner joiner = new StringJoiner(",", "(", ")");
        for (int i = 0; i < 10; i++) {
            joiner.add("文字:" + i);
        }
        System.out.println(joiner);*/
        /*Field[] fields = ReflectUtil.getFields(BaseEntityDTO.class);
        Stream<String> ignoreProperties = Arrays.stream(fields).toList().stream().map(Field::getName);
        String [] dd = ignoreProperties.toArray(String[]::new);*/

        /*LocalDateTime minTime = LocalDateTime.now();
        LocalDateTime maxTime = LocalDateTime.of(LocalDate.from(minTime.plusDays(1)), LocalDateTime.MAX.toLocalTime());
        Duration duration = Duration.between(minTime,maxTime);
        System.out.println(duration.getSeconds());*/

        /*DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
        // 周一
        LocalDate monday = LocalDate.parse(LocalDateTime.now().minusDays(dayOfWeek.getValue() - 1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        // 周日
        LocalDate sunday = LocalDate.parse(monday.plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        for (LocalDate date = monday; date.isBefore(sunday); date = date.plusDays(1)) {
            System.out.println(date);
        }*/
        /*LocalDate startDate = LocalDate.parse("2025-09".concat("-01"));
        LocalDateTime startOfMonth = startDate.atTime(LocalTime.MIN);
        LocalDateTime endOfMonth = startDate.plusMonths(1).minusDays(1).atTime(LocalTime.MAX);
        System.out.println(startOfMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+"------"+endOfMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));*/
        System.out.println("周数:" + getWeeksSpanningMonth(2025, 2));

        LocalDate currentDate = LocalDate.now(); // 可替换为任意指定日期
        int weekOfYear = getWeekOfYear(currentDate);
        System.out.println("当前日期在今年的第 " + weekOfYear + " 周");

        int year = 2025;
        int week = 44;

        Pair<LocalDate, LocalDate> pair = getWeekStartAndEnd(year, week);

        System.out.println("第" + week + "周开始时间: " + pair.getKey());
        System.out.println("第" + week + "周结束时间: " + pair.getValue());
    }

    public static int getWeeksSpanningMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDay = yearMonth.atDay(1);
        LocalDate lastDay = yearMonth.atEndOfMonth();

        WeekFields weekFields = WeekFields.ISO; // 使用ISO标准

        // 获取该月跨越的周数范围
        int firstDayWeek = firstDay.get(weekFields.weekOfYear());
        int lastDayWeek = lastDay.get(weekFields.weekOfYear());

        // 处理跨年情况
        if (lastDayWeek < firstDayWeek) {
            // 跨年情况，需要特殊处理
            return (52 - firstDayWeek) + lastDayWeek + 1;
        } else {
            return lastDayWeek - firstDayWeek + 1;
        }
    }

    public static int getWeekOfYear(LocalDate date) {
        // 使用默认地区的周计算规则
        return date.get(WeekFields.of(Locale.getDefault()).weekOfYear());
    }

    /**
     * 获取指定年份第几周的开始日期和结束日期
     *
     * @param year       年份
     * @param weekOfYear 周数
     * @return 包含开始日期和结束日期的数组
     */
    public static Pair<LocalDate, LocalDate> getWeekStartAndEnd(int year, int weekOfYear) {
        WeekFields weekFields = WeekFields.ISO; // 使用ISO标准，周一为每周第一天

        // 获取该周的第一天（周一）
        LocalDate startDate = LocalDate.ofYearDay(year, 1)
                .with(weekFields.weekOfYear(), weekOfYear)
                .with(weekFields.dayOfWeek(), 1);

        // 获取该周的最后一天（周日）
        LocalDate endDate = startDate.with(weekFields.dayOfWeek(), 7);
        return Pair.of(startDate, endDate);
    }
}
