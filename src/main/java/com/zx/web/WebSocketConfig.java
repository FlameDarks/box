package com.zx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.*;

@Component("WebSocketConfig")
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private ChatWebSocketHandler webSocketHandler;
    @Autowired
    private ChatHandshakeInterceptor chatHandshakeInterceptor;

    /**
     * 配置Websocket
     * @param registry
     */
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //添加一个处理器还有定义处理器的处理路径
        registry.addHandler(webSocketHandler, "/ws").addInterceptors(chatHandshakeInterceptor);
        /*
         * 在这里我们用到.withSockJS()，SockJS是spring用来处理浏览器对websocket的兼容性，
         * 目前浏览器支持websocket还不是很好，特别是IE11以下.
         * SockJS能根据浏览器能否支持websocket来提供三种方式用于websocket请求，
         * 三种方式分别是 WebSocket, HTTP Streaming以及 HTTP Long Polling
         */
        registry.addHandler(webSocketHandler, "/ws/sockjs").addInterceptors(chatHandshakeInterceptor).withSockJS();
    }
}
