package com.zx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Component("WebSocketConfig")
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private ChatWebSocketHandler webSocketHandler;
    @Autowired
    private ChatHandshakeInterceptor chatHandshakeInterceptor;

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


//    /**
//     * 定义接收/websocket时采用wensocket连接，添加HttpSessionHandshakeInterceptor是为了websocket握手前将httpsession中的属性
//     * 添加到websocket session中，withSockJS添加对sockJS的支持
//     *
//     * @param stompEndpointRegistry
//     */
//    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
//        stompEndpointRegistry.addEndpoint("/websocket").setAllowedOrigins("*").addInterceptors(new HttpSessionHandshakeInterceptor()).withSockJS();
//    }
//
//    /**
//     * 配置消息代理，以/app为头的url将会先经过MessageMapping
//     * /topic直接进入消息代理
//     *
//     * @param registry
//     */
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.setApplicationDestinationPrefixes("/app");
//        registry.enableSimpleBroker("/topic");
//    }
}
