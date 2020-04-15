package com.zx.service;

import com.zx.bean.Chat;
import com.zx.bean.ChatExample;
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

    public void insert(Chat chat) {
        chatMapper.insertSelective(chat);
    }

    public Chat getUserName(Integer userId) {
        ChatExample chatExample = new ChatExample();
        ChatExample.Criteria criteria = chatExample.createCriteria();
        criteria.andChatUserIdEqualTo(userId);
        List<Chat> chats= chatMapper.selectByExampleWithUserName(chatExample);
        System.out.println("获取到的名字："+chats.get(-1));
        return chats.get(-1);
    }
}
