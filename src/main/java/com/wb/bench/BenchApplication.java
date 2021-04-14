package com.wb.bench;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLOutput;

@SpringBootApplication
public class BenchApplication {
    public static void main(String[] args) {
        SpringApplication.run(BenchApplication.class, args);
        System.out.println("spring start..........");
    }
}
