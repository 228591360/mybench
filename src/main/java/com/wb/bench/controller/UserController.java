package com.wb.bench.controller;

import com.wb.bench.entity.User;
import com.wb.bench.service.UserServer;
import com.wb.bench.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WangBiao
 * @Date: 2020/12/9 16:03
 */
@Slf4j
@RestController
public class UserController {
    @Resource
    private UserServer userServer;
    @GetMapping("/query")
    public List<User> query(){
        return userServer.queryUser();
    }

    @GetMapping("/queryUserById")
    public User queryUserById(@Param("id") Integer id) {
        log.info("id:{}",id);
        return userServer.queryUserById(id);
    }

}
