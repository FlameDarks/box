package com.zx.controller;

import com.zx.bean.Chat;
import com.zx.bean.Msg;
import com.zx.bean.User;
import com.zx.service.ChatService;
import com.zx.service.UserService;
import com.zx.web.ChatWebSocketHandler;
import com.zx.web.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Controller
public class MessageController {

    private static final String IMAGE_PREFIX = "/static/images/upload/";

    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;

    @Autowired
    private ChatWebSocketHandler handler;

    /**
     * 接收转发图片
     * @param request
     * @param imageFile
     * @param userId
     * @return
     */
    @RequestMapping(value = "/upload/image", method = RequestMethod.POST)
    @ResponseBody
    public Msg handleUploadImage(HttpServletRequest request, @RequestParam("image") MultipartFile imageFile,
                                 @RequestParam("userId")Integer userId){
        if (!imageFile.isEmpty()){
            String imageName = userId + "_" + UUID.randomUUID().toString().substring(0,5) + ".jpg";
            String path = request.getSession().getServletContext().getRealPath(IMAGE_PREFIX)  +"/" + imageName;
            File localImageFile = new File(path);
            try {
                //上传图片到目录
                byte[] bytes = imageFile.getBytes();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localImageFile));
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.close();
                Chat message = new Chat();
                message.setChatType(2);
                message.setChatUserId(userId);
                message.setUserName(userService.getUser(userId).getUserName());
                message.setChatTime(new Date());
                message.setChatContent(request.getContextPath() + IMAGE_PREFIX + imageName);
                //保存发送图片信息
//                Chat userByName = chatService.getUserName(message.getChatUserId());
//                Chat chat = Chat.builder().userId(userByName == null ? null : userByName.getChatUserId())
//                        .chatContent(message.getChatContent()).chatTime(new Date()).build();
                chatService.insert(message);
                TextMessage msg = new TextMessage(GsonUtils.toJson(message));
                handler.sendMessageToAll(msg);
            } catch (IOException e) {
                System.out.println("图片上传失败：" + e.getMessage());
                return Msg.fail();
            }
        }else {
            return Msg.fail();
        }
        return Msg.success();
    }
}
