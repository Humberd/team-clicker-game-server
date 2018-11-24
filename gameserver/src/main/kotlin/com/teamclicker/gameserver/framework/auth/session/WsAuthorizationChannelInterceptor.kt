package com.teamclicker.gameserver.framework.auth.session

import com.teamclicker.gameserver.framework.auth.jwt.services.JwtAuthHeaderExtractor
import mu.KLogging
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand.CONNECT
import org.springframework.messaging.simp.stomp.StompCommand.DISCONNECT
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.stereotype.Service

@Service
class WsAuthorizationChannelInterceptor(
    private val jwtAuthHeaderExtractor: JwtAuthHeaderExtractor
) : ChannelInterceptor {
    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        val accessor =
            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)!!

        when (accessor.command) {
            CONNECT -> onConnect(accessor)
            DISCONNECT -> onDisconnect(accessor)
        }

        return message
    }

    private fun onConnect(accessor: StompHeaderAccessor) {
        val auth = jwtAuthHeaderExtractor.extractCredentials(accessor)
        if (auth.isPresent) {
            accessor.user = auth.get()
            logger.info { "User ${accessor.user?.name} authorized." }
        }
    }

    private fun onDisconnect(accessor: StompHeaderAccessor) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object : KLogging()

}