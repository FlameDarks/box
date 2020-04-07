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

    public List<Contact> getAll(Integer userid) {
        ContactExample contactExample = new ContactExample();
        ContactExample.Criteria criteria = contactExample.createCriteria();
        criteria.andUserIdEqualTo(userid);
        return contactMapper.selectByExample(contactExample);
    }

    public void save(Contact contact) {
        contactMapper.insertSelective(contact);
    }

    public Contact echocontacts(Integer id) {
        return contactMapper.selectByPrimaryKey(id);
    }

    public void update(Contact contact) {
        contactMapper.updateByPrimaryKeySelective(contact);
    }

    public void delete(Integer id) {
        contactMapper.deleteByPrimaryKey(id);
    }

    public void deleteAll(List<Integer> ids) {
        ContactExample contactExample = new ContactExample();
        ContactExample.Criteria criteria = contactExample.createCriteria();
        criteria.andContactIdIn(ids);
        contactMapper.deleteByExample(contactExample);
    }
}
