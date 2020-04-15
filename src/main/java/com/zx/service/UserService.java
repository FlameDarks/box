package com.zx.service;

import com.mysql.cj.x.protobuf.MysqlxNotice;
import com.zx.bean.User;
import com.zx.bean.UserExample;
import com.zx.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.swing.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public void reg(User user) {
        System.out.println("进入Service");
        String pwd = user.getUserPassword();
        String base = pwd.substring(0,1)+pwd+pwd.substring(pwd.length()-1,pwd.length());
        user.setUserPassword(DigestUtils.md5DigestAsHex(base.getBytes()));
        System.out.println("name:"+user.getUserName()+"password:"+user.getUserPassword());
        userMapper.insertSelective(user);
    }


    public boolean checkUser(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(username);
        long count = userMapper.countByExample(userExample);
        System.out.println("找到："+count);
        return count == 0;
    }
//    通过Id获取用户信息
    public User getUser(Integer id){
        User user = userMapper.selectByPrimaryKey(id);
        user.setUserPassword(null);
        return user;
    }
}

