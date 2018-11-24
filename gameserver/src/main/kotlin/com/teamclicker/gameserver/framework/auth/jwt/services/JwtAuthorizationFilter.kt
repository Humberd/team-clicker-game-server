package com.teamclicker.gameserver.framework.auth.jwt.services

import mu.KLogging
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.function.Function

@Service
class JwtAuthorizationFilter(
    private val jwtAuthHeaderExtractor: JwtAuthHeaderExtractor
) : Function<ServerWebExchange, Mono<Authentication>> {

    override fun apply(t: ServerWebExchange): Mono<Authentication> {
        return Mono.justOrEmpty(t)
            .flatMap(this::filter)
    }

    fun filter(exchange: ServerWebExchange): Mono<Authentication> {
        val auth = jwtAuthHeaderExtractor.extractCredentials(exchange.request)
        if (auth.isPresent) {
            return Mono.just(auth.get())
        }
        return Mono.empty()
    }

    companion object : KLogging()
}