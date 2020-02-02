package com.majiang.springboot.service;

import com.majiang.springboot.mapper.UserMapper;
import com.majiang.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        //根据github中的account_id从数据库中查询用户
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        //如果数据库中没有相关用户就创建一个新的用户，否则更新此用户
        if(null==dbUser){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            //需要从新复制修改时间、头像、名字、token并执行更新操作
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            userMapper.update(dbUser);
        }
    }
}
