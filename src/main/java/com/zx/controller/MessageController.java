package com.zx.controller;

import com.zx.bean.Chat;
import com.zx.bean.Message;
import com.zx.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Controller
public class MessageController {

    private static final String IMAGE_PREFIX = "/resources/media/image/";

    @Autowired
    private ChatService chatService;

    /**
     * 接收转发图片
     * @param request
     * @param imageFile
     * @param userName
     * @return
     */
    @RequestMapping(value = "/upload/image", method = RequestMethod.POST)
    @ResponseBody
    public String handleUploadImage(HttpServletRequest request, @RequestParam("image") MultipartFile imageFile,
                                    @RequestParam("userName")String userName){
        if (!imageFile.isEmpty()){
            String imageName = userName + "_" + (int)(Math.random() * 1000000) + ".jpg";
            String path = request.getSession().getServletContext().getRealPath(IMAGE_PREFIX)  +"/" + imageName;
            File localImageFile = new File(path);
            try {
                //上传图片到目录
                byte[] bytes = imageFile.getBytes();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localImageFile));
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.close();
                Message message = new Message();
                message.setMessageType("image");
                message.setUserName(userName);
                message.setSendDate(new Date());
                message.setContent(request.getContextPath() + IMAGE_PREFIX + imageName);
                //保存发送图片信息
                Chat userByName = chatService.getUserName(message.getUserId());
                Chat chat = Chat.builder().userId(userByName == null ? null : userByName.getChatUserId())
                        .chatContent(message.getContent()).chatTime(new Date()).build();
                chatService.insert(chat);

            } catch (IOException e) {
                System.out.println("图片上传失败：" + e.getMessage());
                return "upload false";
            }
        }
        return "upload success";
    }
}
