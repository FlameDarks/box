package com.zx.service;

import com.zx.bean.Contact;
import com.zx.bean.ContactExample;
import com.zx.dao.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    ContactMapper contactMapper;

    /**
     * 获取用户ID下所有通讯录信息
     * @param userId
     * @return
     */
    public List<Contact> getAll(Integer userId) {
        ContactExample contactExample = new ContactExample();
        ContactExample.Criteria criteria = contactExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return contactMapper.selectByExample(contactExample);
    }

    /**
     * 插入通讯录
     * @param contact
     */
    public void save(Contact contact) {
        contactMapper.insertSelective(contact);
    }

    /**
     * 通过Id获取通讯录信息
     * @param id
     * @return
     */
    public Contact echocontacts(Integer id) {
        return contactMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新通讯录
     * @param contact
     */
    public void update(Contact contact) {
        contactMapper.updateByPrimaryKeySelective(contact);
    }

    /**
     * 删除通讯录
     * @param id
     */
    public void delete(Integer id) {
        contactMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除通讯录
     * @param ids
     */
    public void deleteAll(List<Integer> ids) {
        ContactExample contactExample = new ContactExample();
        ContactExample.Criteria criteria = contactExample.createCriteria();
        criteria.andContactIdIn(ids);
        contactMapper.deleteByExample(contactExample);
    }

    /**
     * 搜索通讯录
     * @param userId
     * @param data
     * @param check
     * @return
     */
    public List<Contact> select(Integer userId, String data, Integer check) {
        ContactExample contactExample = new ContactExample();
        ContactExample.Criteria criteria = contactExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if (check == 1){
            criteria.andContactNameLike(data);
        }else if (check == 2){
            criteria.andContactPhoneLike(data);
        }else{
            criteria.andContactAddressLike(data);
        }
        return contactMapper.selectByExample(contactExample);
    }
}
