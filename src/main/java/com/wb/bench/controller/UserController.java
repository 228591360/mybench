package com.wb.bench.controller;

import com.wb.bench.common.R;
import com.wb.bench.common.Result;
import com.wb.bench.entity.User;
import com.wb.bench.service.UserServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/queryUser")
    public Result<List<User>> query() {
        return R.ok(userServer.queryUser());
    }

    @GetMapping("/queryUserById")
    public Result<User> queryUserById(@Param("id") String id) {
        return R.ok(userServer.queryUserById(id));
    }

    @GetMapping("/deleteUserById")
    public Result deleteUserById(@Param("id") String id) {
        userServer.deleteUserById(id);
        return R.ok();
    }

    @PostMapping("/createUser")
    public Result createUser(@RequestBody @Validated User user) {
        userServer.createUser(user);
        return R.ok();
    }

}
