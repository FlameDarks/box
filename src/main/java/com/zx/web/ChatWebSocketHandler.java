package com.zx.web;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zx.bean.Chat;
import com.zx.bean.User;
import com.zx.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@Component("chatWebSocketHandler")
public class ChatWebSocketHandler implements WebSocketHandler {

    @Autowired
    ChatService chatService;
    //在线用户的SOCKETsession(存储了所有的通信通道)
    public static final Map<String, WebSocketSession> USER_SOCKETSESSION_MAP;
    //图片上传目录
    private static final String IMAGE_PREFIX = "/resources/image/";

    //存储所有的在线用户
    static {
        USER_SOCKETSESSION_MAP = new HashMap<String, WebSocketSession>();
    }

    /**
     * webscoket建立好链接之后的处理函数--连接建立后的准备工作
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        //将当前的连接的用户会话放入MAP,key是用户编号
        User loginUser=(User) webSocketSession.getAttributes().get("loginUser");
        USER_SOCKETSESSION_MAP.put(String.valueOf(loginUser.getUserId()), webSocketSession);

        //群发消息告知大家
        Chat msg = new Chat();
//        msg.setContent("风骚的【"+loginUser.getUserName()+"】踩着轻盈的步伐来啦。。。大家欢迎！");
        msg.setUserName(loginUser.getUserName());
        msg.setType(true);
        msg.setChatType(0);
        msg.setChatTime(new Date());
        //获取所有在线的WebSocketSession对象集合
        Set<Entry<String, WebSocketSession>> entrySet = USER_SOCKETSESSION_MAP.entrySet();
        //将最新的所有的在线人列表放入消息对象的list集合中，用于页面显示
        for (Entry<String, WebSocketSession> entry : entrySet) {
            msg.getUserList().add((User)entry.getValue().getAttributes().get("loginUser"));
        }

        //将消息转换为json
        TextMessage message = new TextMessage(GsonUtils.toJson(msg));
        //群发消息
        sendMessageToAll(message);

    }

    @Override
    /**
     * 客户端发送服务器的消息时的处理函数，在这里收到消息之后可以分发消息
     */
    //处理消息：当一个新的WebSocket到达的时候，会被调用（在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理）
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> chat) throws Exception {
        //如果消息没有任何内容，则直接返回
        if(chat.getPayloadLength()==0)return;
        //反序列化服务端收到的json消息
        Chat msg = GsonUtils.fromJson(chat.getPayload().toString(), Chat.class);
        msg.setChatTime(new Date());
        //处理html的字符，转义：
//        String text = msg.getContent();
        //转换为HTML转义字符表示
//        String htmlEscapeText = HtmlUtils.htmlEscape(text);
//        JSONObject jsonObject = new JSONObject(chat.getPayload().toString());
//        msg.setChatContent(jsonObject.getStr("chatContent"));
//        System.out.println(msg.toString());
        msg.setChatType(1);
//        msg.setChatUserId(jsonObject.getInt("userId"));
        System.out.println("消息（可存数据库作为历史记录）:"+chat.getPayload().toString());
        chatService.insert(msg);
        //判断是群发还是单发
//        if(msg.getToUser()==null||msg.getToUser().equals("-1")){
            //群发
            sendMessageToAll(new TextMessage(GsonUtils.toJson(msg)));
//        }else{
//            //单发
//            sendMessageToUser(msg.getToUser(), new TextMessage(GsonUtils.toJson(msg)));
//        }
    }

    @Override
    /**
     * 消息传输过程中出现的异常处理函数
     * 处理传输错误：处理由底层WebSocket消息传输过程中发生的异常
     */
    public void handleTransportError(WebSocketSession webSocketSession, Throwable exception) throws Exception {
        // 记录日志，准备关闭连接
        System.out.println("Websocket异常断开:" + webSocketSession.getId() + "已经关闭");
        //一旦发生异常，强制用户下线，关闭session
        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }

        //群发消息告知大家
        Chat msg = new Chat();
        msg.setChatTime(new Date());

        //获取异常的用户的会话中的用户编号
        User loginUser=(User)webSocketSession.getAttributes().get("loginUser");
        //获取所有的用户的会话
        Set<Entry<String, WebSocketSession>> entrySet = USER_SOCKETSESSION_MAP.entrySet();
        //并查找出在线用户的WebSocketSession（会话），将其移除（不再对其发消息了。。）
        for (Entry<String, WebSocketSession> entry : entrySet) {
            if(entry.getKey().equals(String.valueOf(loginUser.getUserId()))){
//                msg.setContent("万众瞩目的【"+loginUser.getUserName()+"】已经退出。。。！");
                //清除在线会话
                msg.setType(false);
                msg.setUserName(loginUser.getUserName());
                USER_SOCKETSESSION_MAP.remove(entry.getKey());
                //记录日志：
                System.out.println("Socket会话已经移除:用户ID" + entry.getKey());
                break;
            }
        }

        //并查找出在线用户的WebSocketSession（会话），将其移除（不再对其发消息了。。）
        for (Entry<String, WebSocketSession> entry : entrySet) {
            msg.getUserList().add((User)entry.getValue().getAttributes().get("loginUser"));
        }

        TextMessage message = new TextMessage(GsonUtils.toJson(msg));
        sendMessageToAll(message);

    }

    @Override
    /**
     * websocket链接关闭的回调
     * 连接关闭后：一般是回收资源等
     */
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        // 记录日志，准备关闭连接
        System.out.println("Websocket正常断开:" + webSocketSession.getId() + "已经关闭");

        //群发消息告知大家
        Chat msg = new Chat();
        msg.setChatTime(new Date());

        //获取异常的用户的会话中的用户编号
        User loginUser=(User)webSocketSession.getAttributes().get("loginUser");
        System.out.println("离线的人："+loginUser.toString());
        Set<Entry<String, WebSocketSession>> entrySet = USER_SOCKETSESSION_MAP.entrySet();
        //并查找出在线用户的WebSocketSession（会话），将其移除（不再对其发消息了。。）
        System.out.println(entrySet.toString());
        int i=0;
        for (Entry<String, WebSocketSession> entry : entrySet) {
            System.out.println(i++);
            if(entry.getKey().equals(String.valueOf(loginUser.getUserId()))){
                System.out.println("找到了");
                //群发消息告知大家
//                msg.setContent("万众瞩目的【"+loginUser.getUserName()+"】已经有事先走了，大家继续聊...");

                msg.setUserName(loginUser.getUserName());
                System.out.println("登出人的姓名："+msg.getUserName());;
                msg.setType(false);
                //清除在线会话
                USER_SOCKETSESSION_MAP.remove(entry.getKey());
                //记录日志：
                System.out.println("Socket会话已经移除:用户ID" + entry.getKey());
                break;
            }
        }

        //并查找出在线用户的WebSocketSession（会话），将其移除（不再对其发消息了。。）
        for (Entry<String, WebSocketSession> entry : entrySet) {
            msg.getUserList().add((User)entry.getValue().getAttributes().get("loginUser"));
        }

        TextMessage message = new TextMessage(GsonUtils.toJson(msg));
        sendMessageToAll(message);
    }

    @Override
    /**
     * 是否支持处理拆分消息，返回true返回拆分消息
     */
    //是否支持部分消息：如果设置为true，那么一个大的或未知尺寸的消息将会被分割，并会收到多次消息（会通过多次调用方法handleMessage(WebSocketSession, WebSocketMessage). ）
    //如果分为多条消息，那么可以通过一个api：org.springframework.web.socket.WebSocketMessage.isLast() 是否是某条消息的最后一部分。
    //默认一般为false，消息不分割
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     *
     * 说明：给某个人发信息
     * @param id
     * @param message
     */
    private void sendMessageToUser(String id, TextMessage message) throws IOException{
        //获取到要接收消息的用户的session
        WebSocketSession webSocketSession = USER_SOCKETSESSION_MAP.get(id);
        if (webSocketSession != null && webSocketSession.isOpen()) {
            //发送消息
            webSocketSession.sendMessage(message);
        }
    }

//    public void showImage() {
//        if (!imageFile.isEmpty()) {
//            String imageName = userName + "_" + (int) (Math.random() * 1000000) + ".jpg";
//            String path = request.getSession().getServletContext().getRealPath(IMAGE_PREFIX) + "/" + imageName;
//            File localImageFile = new File(path);
//            try {
//                //上传图片到目录
//                byte[] bytes = imageFile.getBytes();
//                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localImageFile));
//                bufferedOutputStream.write(bytes);
//                bufferedOutputStream.close();
//                Chat message = new Chat();
//                message.setChatType(2);
//                message.setUserName(userName);
//                message.setChatTime(new Date());
//                message.setChatContent(request.getContextPath() + IMAGE_PREFIX + imageName);
//                //保存发送图片信息
//                Chat userByName = chatService.getUserName(message.getChatUserId());
//                Chat chat = Chat.builder().userId(userByName == null ? null : userByName.getChatUserId())
//                        .chatContent(message.getChatContent()).chatTime(new Date()).build();
//                chatService.insert(chat);
//            } catch (IOException e) {
//                System.out.println("图片上传失败：" + e.getMessage());
//            }
//        }
//    }

    /**
     *
     * 说明：群发信息：给所有在线用户发送消息
     */
    public void sendMessageToAll(final TextMessage message){
        //对用户发送的消息内容进行转义

        //获取到所有在线用户的SocketSession对象
        Set<Entry<String, WebSocketSession>> entrySet = USER_SOCKETSESSION_MAP.entrySet();
        for (Entry<String, WebSocketSession> entry : entrySet) {
            //某用户的WebSocketSession
            final WebSocketSession webSocketSession = entry.getValue();
            //判断连接是否仍然打开的
            if(webSocketSession.isOpen()){
                //开启多线程发送消息（效率高）
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            if (webSocketSession.isOpen()) {
                                webSocketSession.sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }).start();

            }
        }
    }
}