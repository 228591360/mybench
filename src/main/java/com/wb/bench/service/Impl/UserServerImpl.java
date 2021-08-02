package com.wb.bench.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wb.bench.entity.User;
import com.wb.bench.mapper.UserMapper;
import com.wb.bench.service.UserServer;
import com.wb.bench.util.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public User queryUserById(String id) {
        return userMapper.selectById(id);
    }

    @Override
    public int deleteUserById(String id) {
        return userMapper.deleteById(id);
    }

    @Override
    public int createUser(User user) {
        return userMapper.insert(user);
    }

    public static void main(String[] args) {
        Map userInfoMap = new HashMap();
        userInfoMap.put("age","26");
        userInfoMap.put("name","用户名XXX");
        User user = new User();
        User userParse = JSON.parseObject(JSON.toJSONString(userInfoMap), User.class);
        BeanUtils.copyProperties(userParse,user);
        System.out.println(user);

    }
}
