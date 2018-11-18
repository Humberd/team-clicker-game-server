package com.teamclicker.gameserver.framework.auth.jwt.models

import org.springframework.security.core.authority.SimpleGrantedAuthority

typealias AccountId = Long

data class JwtData(
    val accountId: AccountId,
    val authenticationMethod: JwtAuthenticationMethod,
    val roles: Set<String>
) {
    fun getGrantedAuthorities() =
        roles.map { SimpleGrantedAuthority(it) }


    fun `is`(role: String): Boolean {
        return roles.indexOf(role) >= 0
    }
}

