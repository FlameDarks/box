package com.zx.web;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zx.bean.Chat;
import com.zx.bean.User;
import com.zx.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

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
    //在线用户列表
    public static final Map<String, WebSocketSession> USER_SOCKETSESSION_MAP;

    public static final Map<String, Long> USER_GETOUT_MAP;
    //图片上传目录
    private static final String IMAGE_PREFIX = "/resources/image/";

    //存储所有的在线用户和禁言用户
    static {
        USER_SOCKETSESSION_MAP = new HashMap<String, WebSocketSession>();
        USER_GETOUT_MAP = new HashMap<String, Long>();
    }

    /**
     * 成功连接后，向其他在线用户发送上线消息
     * @param webSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        //将当前的连接的用户会话放入MAP,key是用户ID
        User loginUser=(User) webSocketSession.getAttributes().get("loginUser");
        USER_SOCKETSESSION_MAP.put(String.valueOf(loginUser.getUserId()), webSocketSession);
        //群发消息告知大家
        Chat msg = new Chat();
        msg.setUserName(loginUser.getUserName());
        msg.setType(0);
        msg.setChatType(0);
        msg.setChatTime(new Date());
        //获取在线用户列表
        Set<Entry<String, WebSocketSession>> entrySet = USER_SOCKETSESSION_MAP.entrySet();
        //将最新的所有的在线人列表放入消息对象的list集合中，用于页面显示
        for (Entry<String, WebSocketSession> entry : entrySet) {
            msg.getUserList().add((User)entry.getValue().getAttributes().get("loginUser"));
        }
        JSONObject jsonObject = JSONUtil.parseObj(msg);;
        //群发消息
        sendMessageToAll(new TextMessage(jsonObject.toStringPretty()));
    }

    /**
     * 用户发送消息
     * @param webSocketSession
     * @param chat
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> chat) throws Exception {
        //如果消息没有任何内容，则直接返回
        if(chat.getPayloadLength()==0)return;
//        判断是否被禁言
        boolean b = false;
        //反序列化服务端收到的json消息
        Chat msg = GsonUtils.fromJson(chat.getPayload().toString(), Chat.class);
//        获取用户信息
        User loginUser=(User) webSocketSession.getAttributes().get("loginUser");
//        获取禁言用户列表
        Set<Map.Entry<String, Long>> entrySets = USER_GETOUT_MAP.entrySet();
        for (Map.Entry<String, Long> entry : entrySets) {
            if(entry.getKey().equals(String.valueOf(loginUser.getUserId()))){
                b=true;
//                判断是否已过禁言时间
                if (new Date().getTime()>=entry.getValue()){
                    b=false;
                }
                break;
            }
        }
        if (b==false){
            msg.setChatTime(new Date());
            msg.setChatType(1);
            msg.setType(1);
            chatService.insert(msg);
            //将消息转换为json
            JSONObject jsonObject = JSONUtil.parseObj(msg);;
            sendMessageToAll(new TextMessage(jsonObject.toStringPretty()));
        }
    }

    /**
     * 用户的客户端发生异常
     * @param webSocketSession
     * @param exception
     * @throws Exception
     */
    @Override
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
        //查出离线用户并移除
        for (Entry<String, WebSocketSession> entry : entrySet) {
            if(entry.getKey().equals(String.valueOf(loginUser.getUserId()))){
                //清除在线会话
                msg.setType(2);
                msg.setChatType(0);
                msg.setUserName(loginUser.getUserName());
                USER_SOCKETSESSION_MAP.remove(entry.getKey());
                break;
            }
        }
        //获取移除后的在线用户列表
        for (Entry<String, WebSocketSession> entry : entrySet) {
            msg.getUserList().add((User)entry.getValue().getAttributes().get("loginUser"));
        }
        JSONObject jsonObject = JSONUtil.parseObj(msg);;
        sendMessageToAll(new TextMessage(jsonObject.toStringPretty()));

    }

    /**
     * 用户离线
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        // 记录日志，准备关闭连接
        System.out.println("Websocket正常断开:" + webSocketSession.getId() + "已经关闭");
        //群发消息告知大家
        Chat msg = new Chat();
        msg.setChatTime(new Date());
        //获取异常的用户的会话中的用户编号
        User loginUser=(User)webSocketSession.getAttributes().get("loginUser");
        Set<Entry<String, WebSocketSession>> entrySet = USER_SOCKETSESSION_MAP.entrySet();
        //查出离线用户并移除
        for (Entry<String, WebSocketSession> entry : entrySet) {
            if(entry.getKey().equals(String.valueOf(loginUser.getUserId()))){
                //群发消息告知大家
                msg.setUserName(loginUser.getUserName());
                msg.setType(2);
                msg.setChatType(0);
                //清除在线会话
                USER_SOCKETSESSION_MAP.remove(entry.getKey());
                break;
            }
        }

        //获取移除后的在线用户列表
        for (Entry<String, WebSocketSession> entry : entrySet) {
            msg.getUserList().add((User)entry.getValue().getAttributes().get("loginUser"));
        }
        JSONObject jsonObject = JSONUtil.parseObj(msg);
        sendMessageToAll(new TextMessage(jsonObject.toStringPretty()));
    }

    /**
     * 是否支持处理拆分消息，返回true返回拆分消息
     * 是否支持部分消息：如果设置为true，那么一个大的或未知尺寸的消息将会被分割，并会收到多次消息（会通过多次调用方法handleMessage(WebSocketSession, WebSocketMessage). ）
     * 如果分为多条消息，那么可以通过一个api：org.springframework.web.socket.WebSocketMessage.isLast() 是否是某条消息的最后一部分。
     * 默认一般为false，消息不分割
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 群发消息
     * @param message
     */
    public void sendMessageToAll(final TextMessage message){
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
