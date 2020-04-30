package com.zx.service;

import com.zx.bean.NoteBook;
import com.zx.bean.User;
import com.zx.bean.UserExample;
import com.zx.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    UserMapper userMapper;

    public List<User> getAll() {
//        List<User> users = userMapper.selectByExample(null);
//        List<User> userList = new ArrayList<>();
//        for (User user:users){
//            user.setUserPassword(null);
//            System.out.println(user.toString());
//            userList.add(user);
//        }
        return userMapper.selectByExample(null);
    }

    public void save(User user) {
        userMapper.insertSelective(user);
    }

    public void deleteAll(List<Integer> del_ids) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserIdIn(del_ids);
        userMapper.deleteByExample(userExample);
    }

    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public List<User> select(String data,Integer check){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (check==1){
            String[] strArray = data.split("%");
            criteria.andUserIdEqualTo(Integer.parseInt(strArray[1]));
        }else if (check==2){
            criteria.andUserNameLike(data);
        }else if (check==3){
            criteria.andUserTypeLike(data);
        }
//        List<User> users = userMapper.selectByExample(userExample);
//        List<User> userList = new ArrayList<>();
//        for (User user:users){
//            user.setUserPassword(null);
//            userList.add(user);
//        }
        return userMapper.selectByExample(userExample);
    }
}
