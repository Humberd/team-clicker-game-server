package com.teamclicker.gameserver.framework.transport

import mu.KLogging
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

class MyWebSocketHandler : WebSocketHandler {
    override fun handle(session: WebSocketSession): Mono<Void> {
        return session.receive()
            .doOnNext {
                logger.info { session.handshakeInfo.headers }
                logger.info {
                    session.send(Mono.just(session.textMessage("hello")))
                        .subscribe()
                    "HELLO FROM HANDLER ${session.id}"
                }
            }
            .then()
    }

    companion object : KLogging()
}