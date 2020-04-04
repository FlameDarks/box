package com.zx.service;

import com.zx.bean.User;
import com.zx.bean.UserExample;
import com.zx.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<User> getAll(String name,String pwd) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(name);
        criteria.andUserPasswordEqualTo(pwd);
        if (userMapper.selectByExample(userExample).isEmpty()){
            return null;
        }
        return userMapper.selectByExample(userExample);
    }
}
