package com.teamclicker.gameserver.framework.transport

import com.teamclicker.gameserver.framework.auth.jwt.JwtAuthenticationToken
import com.teamclicker.gameserver.framework.auth.jwt.services.JwtAuthExtractor
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Service
class MyWebSocketHandler(
    private val jwtAuthExtractor: JwtAuthExtractor
) : WebSocketHandler {
    override fun handle(session: WebSocketSession): Mono<Void> {
        val auth = jwtAuthExtractor.extractCredentials(session)
        if (!auth.isPresent) {
            return Mono.error(Exception("no auth header"))
        }
        val jwtAuth = auth.get() as JwtAuthenticationToken

        return session.receive()
            .doOnNext {
                logger.info { session.handshakeInfo.headers }
                logger.info {
                    session.send(Mono.just(session.textMessage(it.payloadAsText)))
                        .subscribe()
                    "HELLO FROM HANDLER ${session.id}"
                }
            }
            .then()
    }

    companion object : KLogging()
}