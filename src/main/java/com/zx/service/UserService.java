package com.zx.service;

import com.zx.bean.User;
import com.zx.bean.UserExample;
import com.zx.dao.UserMapper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
//    获取user信息，登录
    public List<User> getAll(String name,String pwd) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(name);
        criteria.andUserPasswordEqualTo(pwd);
        System.out.println("111111111");
        if (userMapper.selectByExample(userExample).isEmpty()){
            return null;
        }
        return userMapper.selectByExample(userExample);
    }
//    注册
    public void reg(User user) {
        String results = String.valueOf(new SimpleHash("MD5",user.getUserPassword(),user.getUserName(),1024));
        user.setUserPassword(results);
        userMapper.insertSelective(user);
    }

//    验证用户名是否重复
    public boolean checkUser(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(username);
        long count = userMapper.countByExample(userExample);
        return count == 0;
    }
//    通过Id获取用户信息
    public User getUser(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    public User getUserByName(String userName){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(userName);
        User user = userMapper.selectByExample(userExample).get(0);
        user.setUserPassword(null);
        return user;
    }
//    修改密码
    public void update(User user){
        String results = String.valueOf(new SimpleHash("MD5",user.getUserPassword(),user.getUserName(),1024));
        user.setUserPassword(results);
        userMapper.updateByPrimaryKeySelective(user);
    }
}

