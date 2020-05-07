package com.zx.service;

import com.zx.bean.Chat;
import com.zx.dao.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    ChatMapper chatMapper;

    /**
     * 插入聊天记录
     * @param chat
     */
    public void insert(Chat chat) {
        chatMapper.insertSelective(chat);
    }
}
