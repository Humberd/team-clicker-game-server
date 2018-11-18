package com.teamclicker.gameserver.framework.auth.jwt.resolvers

import com.teamclicker.gameserver.framework.auth.jwt.JwtAuthenticationToken
import com.teamclicker.gameserver.framework.auth.jwt.models.JwtData
import org.springframework.core.MethodParameter
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class JwtResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType.isAssignableFrom(JwtData::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        bindingContext: BindingContext,
        exchange: ServerWebExchange
    ): Mono<Any> {
        return exchange.getPrincipal<JwtAuthenticationToken>()
            .map { it.jwtData }
    }
}