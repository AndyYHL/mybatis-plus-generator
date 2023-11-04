package com.example.generator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.*;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class MybatisPlusGeneratorApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        LocalDate rTime = LocalDate.ofInstant(Instant.ofEpochMilli(1698681600000l), ZoneId.systemDefault());
        System.out.println(rTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        LocalDate date = LocalDate.now();
        LocalDateTime dd = LocalDateTime.parse(LocalDateTime.of(date, LocalTime.MIN).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(dd);
        LocalDateTime dateTime = LocalDateTime.parse("2023-10-31 00:00:00",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(dateTime);
    }
}
