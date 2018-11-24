package com.teamclicker.gameserver.framework.auth.configs

import com.teamclicker.gameserver.framework.auth.session.WsAuthorizationChannelInterceptor
import mu.KLogging
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry

//@Configuration
//@EnableWebSocketMessageBroker
//class WebSocketSecurity(
//    private val wsAuthorizationChannelInterceptor: WsAuthorizationChannelInterceptor
//) : AbstractSecurityWebSocketMessageBrokerConfigurer() {
//    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
//        registry.enableSimpleBroker("/topic", "/queue")
//        registry.setApplicationDestinationPrefixes("/app")
//    }
//
//    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
//        registry.addEndpoint("/ws")
//            .setAllowedOrigins("*")
//            .withSockJS()
//        registry.addEndpoint("/ws")
//            .setAllowedOrigins("*")
//    }
//
//    override fun sameOriginDisabled(): Boolean {
//        return true
//    }
//
//    override fun customizeClientInboundChannel(registration: ChannelRegistration) {
//        registration.interceptors(wsAuthorizationChannelInterceptor)
//    }
//
//    companion object : KLogging()
//}