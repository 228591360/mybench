package com.wb.bench;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class BenchApplicationTests {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    public  void set() {
        redisTemplate.opsForValue().set("myKey","myValue");
        System.out.println(redisTemplate.opsForValue().get("myKey"));
    }
}
