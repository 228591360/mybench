package com.wb.bench.service;

import com.wb.bench.entity.User;

import java.util.List;

/**
 * @Author: WangBiao
 * @Date: 2020/12/8 11:03
 */
public interface UserServer {

    List<User> queryUser();

    User queryUserById(Integer id);

    int deleteUserById(Integer id);
}
