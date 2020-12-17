package com.wb.bench.service;

import com.wb.bench.entity.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author: WangBiao
 * @Date: 2020/12/8 11:03
 */
public interface UserServer {

    List<User> queryUser();

    User queryUserById(String id);

    int deleteUserById(String id);

    int createUser(User user);
}
