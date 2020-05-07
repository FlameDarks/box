package com.zx.service;

import com.zx.bean.User;
import com.zx.bean.UserExample;
import com.zx.dao.UserMapper;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    UserMapper userMapper;

    /**
     * 获取所有用户
     * @return
     */
    public List<User> getAll() {
        return userMapper.selectByExample(null);
    }

    /**
     * 注册用户
     * @param user
     */
    public void save(User user) {
        userMapper.insertSelective(user);
    }

    /**
     * 批量删除用户
     * @param del_ids
     */
    public void deleteAll(List<Integer> del_ids) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUserIdIn(del_ids);
        userMapper.deleteByExample(userExample);
    }

    /**
     * 删除用户
     * @param id
     */
    public void delete(Integer id) {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询用户
     * @param data
     * @param check
     * @return
     */
    @RequiresRoles({"admin"})
    public List<User> select(String data,Integer check){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
//        查询用户ID
        if (check==1){
            String[] strArray = data.split("%");
            criteria.andUserIdEqualTo(Integer.parseInt(strArray[1]));
        }
//        模糊查询用户名
        else if (check==2){
            criteria.andUserNameLike(data);
        }
//        模糊查询类型
        else if (check==3){
            criteria.andUserTypeLike(data);
        }
        return userMapper.selectByExample(userExample);
    }
}
