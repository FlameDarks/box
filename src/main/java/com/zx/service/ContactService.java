package com.zx.service;

import com.zx.bean.Contact;
import com.zx.dao.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    @Autowired
    ContactMapper contactMapper;
    public List<Contact> getAll() {
        return contactMapper.selectByExample(null);
    }
}
