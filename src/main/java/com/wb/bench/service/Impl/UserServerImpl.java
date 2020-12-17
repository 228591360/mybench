package com.wb.bench.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.bench.entity.User;
import com.wb.bench.mapper.UserMapper;
import com.wb.bench.service.UserServer;
import com.wb.bench.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: WangBiao
 * @Date: 2020/12/8 11:15
 */
@Service
public class UserServerImpl extends ServiceImpl<UserMapper, User> implements UserServer {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<User> queryUser() {
        return userMapper.queryUser();
    }

    @Override
    public User queryUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public int deleteUserById(Integer id) {
        return userMapper.deleteById(id);
    }
}
