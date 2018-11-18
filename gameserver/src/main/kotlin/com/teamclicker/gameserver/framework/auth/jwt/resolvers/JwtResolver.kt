package com.teamclicker.gameserver.framework.auth.jwt.resolvers

import com.teamclicker.gameserver.framework.auth.jwt.JwtAuthenticationToken
import com.teamclicker.gameserver.framework.auth.jwt.models.JwtData
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Configuration
class JWTDataResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType.isAssignableFrom(JwtData::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        if (webRequest.userPrincipal === null) {
            return null
        }
        val userPrincipal = webRequest.userPrincipal as JwtAuthenticationToken
        return userPrincipal.jwtData
    }

}