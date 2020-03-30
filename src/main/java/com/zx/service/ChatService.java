package com.zx.service;

import com.zx.bean.Chat;
import com.zx.dao.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    ChatMapper chatMapper;
    public List<Chat> getAll() {
        return chatMapper.selectByExampleWithUserName(null);
    }
}
