package com.zx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.bean.Chat;
import com.zx.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ChatController {

    @Autowired
    ChatService chatService;
    @RequestMapping("/chat")
    public String getChat(@RequestParam(value = "pn",defaultValue = "1")Integer pn, Model model){
        PageHelper.startPage(pn,1);
        List<Chat> chat = chatService.getAll();
        PageInfo pageInfo = new PageInfo(chat,5);
        model.addAttribute("chat_pageInfo",pageInfo);
        return "chat";
    }
    @RequestMapping("/login")
    public String getIndex(){
        return "success";
    }
}
