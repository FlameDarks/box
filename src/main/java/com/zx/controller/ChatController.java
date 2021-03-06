package com.zx.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zx.bean.*;
import com.zx.service.ChatService;
import com.zx.service.UserService;
import com.zx.web.ChatWebSocketHandler;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.zx.web.ChatWebSocketHandler.USER_GETOUT_MAP;
import static com.zx.web.ChatWebSocketHandler.USER_SOCKETSESSION_MAP;


@Controller
public class ChatController {

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
     * @param imageFile 获取文件
     * @param userId
     * @return
     */
    @RequestMapping(value = "/upload/image", method = RequestMethod.POST)
    @ResponseBody
    public Msg handleUploadImage(HttpServletRequest request, @RequestParam("image") MultipartFile imageFile,
                                 @RequestParam("userId")Integer userId){
//        判断文件是否为空
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
//                转变为消息
                Chat message = new Chat();
                message.setChatType(2);
                message.setChatUserId(userId);
                message.setUserName(userService.getUser(userId).getUserName());
                message.setChatTime(new Date());
                message.setChatContent(request.getContextPath() + IMAGE_PREFIX + imageName);
                chatService.insert(message);
//                封装成Json
                JSONObject jsonObject = JSONUtil.parseObj(message);
                TextMessage msg = new TextMessage(jsonObject.toStringPretty());
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

    /**
     * 禁言用户
     * @param userName
     * @return
     */
    @RequiresRoles({"admin"})
    @RequestMapping("/getout")
    public Msg getoutUser(@RequestParam("userName") String userName){
//        获取在线用户列表
        Set<Map.Entry<String, WebSocketSession>> entrySet = USER_SOCKETSESSION_MAP.entrySet();
        User user = userService.getUserByName(userName);
        Chat msg = new Chat();
        Date date = new Date();
        for (Map.Entry<String, WebSocketSession> entry : entrySet) {
            if(entry.getKey().equals(String.valueOf(user.getUserId()))){
                msg.setChatContent(userName+"已被管理员禁言30秒");
                msg.setType(1);
                msg.setChatType(0);
                msg.setUserName(userName);
                msg.setChatTime(date);
                msg.setBan(true);
                USER_GETOUT_MAP.put(String.valueOf(user.getUserId()),date.getTime()+30000);
                break;
            }
        }
//        在线用户列表
        for (Map.Entry<String, WebSocketSession> entry : entrySet) {
            msg.getUserList().add((User)entry.getValue().getAttributes().get("loginUser"));
        }
        JSONObject jsonObject = JSONUtil.parseObj(msg);
        //将消息转换为json
        TextMessage message = new TextMessage(jsonObject.toStringPretty());
        handler.sendMessageToAll(message);
        return Msg.success();
    }

}
