package com.teamclicker.gameserver.framework.transport

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import java.util.HashMap

@Configuration
class WsConfig {
    @Bean
    fun handlerMapping(myWebSocketHandler: MyWebSocketHandler): HandlerMapping {
        val map = HashMap<String, WebSocketHandler>()
        map["/path"] = myWebSocketHandler

        val mapping = SimpleUrlHandlerMapping()
        mapping.setUrlMap(map)
        mapping.setOrder(-1) // before annotated controllers
        return mapping
    }

    @Bean
    fun handlerAdapter(): WebSocketHandlerAdapter {
        return WebSocketHandlerAdapter()
    }
}