package com.teamclicker.gameserver.framework.auth.jwt

import org.springframework.security.core.authority.SimpleGrantedAuthority

data class JwtData(
    val accountId: Long,
    val authenticationMethod: JwtAuthenticationMethod,
    val roles: Set<String>
) {
    fun getGrantedAuthorities() =
        roles.map { SimpleGrantedAuthority(it) }


    fun `is`(role: String): Boolean {
        return roles.indexOf(role) >= 0
    }
}

