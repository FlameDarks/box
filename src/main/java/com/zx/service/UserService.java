package com.zx.service;

import com.zx.bean.User;
import com.zx.bean.UserExample;
import com.zx.dao.UserMapper;
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
        if (userMapper.selectByExample(userExample).isEmpty()){
            return null;
        }
        return userMapper.selectByExample(userExample);
    }
//    注册
    public void reg(User user) {
        System.out.println("进入Service");
        safe(user);
        userMapper.insertSelective(user);
    }

//    验证用户名是否重复
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
        return userMapper.selectByPrimaryKey(id);
    }
//    修改密码
    public void update(User user){
        safe(user);
        userMapper.updateByPrimaryKeySelective(user);
    }
//    加密
    public void safe(User user){
        String pwd = user.getUserPassword();
        String base = pwd.substring(0,1)+pwd+pwd.substring(pwd.length()-1,pwd.length());
        user.setUserPassword(DigestUtils.md5DigestAsHex(base.getBytes()));
        System.out.println("name:"+user.getUserName()+"\tpassword:"+user.getUserPassword());
    }
}

