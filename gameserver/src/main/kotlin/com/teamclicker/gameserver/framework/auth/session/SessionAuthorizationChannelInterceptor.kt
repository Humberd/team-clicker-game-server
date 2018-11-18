package com.teamclicker.gameserver.framework.auth.session

import com.teamclicker.gameserver.framework.auth.session.models.SessionId
import com.teamclicker.gameserver.framework.auth.session.models.WsSession
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor

class SessionAuthorizationChannelInterceptor : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor =
            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)!!

        return null
//        val foo =WsSession("hello", "me")
//        foo.sessionId.
    }
}