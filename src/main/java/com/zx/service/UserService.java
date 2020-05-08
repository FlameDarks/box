package com.zx.service;

import com.zx.bean.User;
import com.zx.bean.UserExample;
import com.zx.dao.UserMapper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 查询用户名是否存在，密码是否正确
     * @param name
     * @param pwd
     * @return
     */
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

    /**
     * 注册用户
     * @param user
     */
    public void reg(User user) {
        String results = String.valueOf(new SimpleHash("MD5",user.getUserPassword(),user.getUserName(),1024));
        user.setUserPassword(results);
        user.setUserType("user");
        userMapper.insertSelective(user);
    }

    /**
     * 验证用户名是否重复
     * @param username
     * @return
     */
    public boolean checkUser(String username) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(username);
        long count = userMapper.countByExample(userExample);
        return count == 0;
    }

    /**
     * 通过Id获取用户信息
     * @param id
     * @return
     */
    public User getUser(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 通过名字获取用户信息
     * @param userName
     * @return
     */
    public User getUserByName(String userName){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserNameEqualTo(userName);
        User user = userMapper.selectByExample(userExample).get(0);
        user.setUserPassword(null);
        return user;
    }

    /**
     * 修改密码
     * @param user
     */
    public void update(User user){
        String results = String.valueOf(new SimpleHash("MD5",user.getUserPassword(),user.getUserName(),1024));
        user.setUserPassword(results);
        userMapper.updateByPrimaryKeySelective(user);
    }
}

