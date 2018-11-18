package com.teamclicker.gameserver.framework.auth.jwt

import com.teamclicker.gameserver.framework.auth.jwt.models.JwtData
import org.springframework.security.authentication.AbstractAuthenticationToken

class JwtAuthenticationToken(
    val jwtData: JwtData
) : AbstractAuthenticationToken(jwtData.getGrantedAuthorities()) {

    init {
        isAuthenticated = true
    }

    override fun getCredentials(): Any {
        return ""
    }

    override fun getPrincipal(): Any {
        return jwtData.accountId.toString()
    }

}