package com.zx.controller;

import com.zx.bean.*;
import com.zx.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ChatController {
    @Autowired
    private ChatService chatService;


    @RequestMapping("/chat")
    public Msg loginIntoChatRoom(User user, HttpServletRequest request){
//        System.out.println("进入/chat");
//        System.out.println(user.toString());
//        if (user.getUserId()==null|| user.getUserName() == null){
//            System.out.println("空空");
//        }else {
//            user.setUserId(user.getUserId());
//            user.setUserName(user.getUserName());
//            user.setLoginDate(new Date());
//            System.out.println("导入完成");
//        }
//        HttpSession session = request.getSession();
//        session.setAttribute("user", user);
//        System.out.println(user.toString());
        //保存登陆信息
//        LoginInfoDo loginInfo = LoginInfoDo.builder().userId(user.getUserId()).userName(user.getUserName()).
//                status(LoginTypeNum.LOGIN.getCode()).createTime(new Date()).build();
//        userDAO.addUserLoginInfo(loginInfo);
//        Info info = Info.builder()
//                .infoStatus(LoginTypeNum.LOGIN.getCode())
//                .infoTime(new Date())
//                .userId(user.getUserId())
//                .build();
//        infoService.insert(info);
//        template.convertAndSend(SUBSCRIBE_LOGIN_URI, user);
//        System.out.println("得到的消息："+template.getMessageConverter());
//        onlineUser.add(user.getUserName(), user);
//        System.out.println("在线的人："+onlineUser.getActiveSessions());
//        logger.info(user.getLoginDate() + ", " + user.getUserName() + " login.");
//        System.out.println(user.getLoginDate() + ", " + user.getUserName() + " login.");
//        System.out.println("/chat完成");
        return Msg.success();
    }

}
