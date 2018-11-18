package com.teamclicker.gameserver.framework.auth.jwt.mappers

import com.teamclicker.gameserver.framework.auth.jwt.models.JwtAuthenticationMethod
import com.teamclicker.gameserver.framework.auth.jwt.models.JwtData
import io.jsonwebtoken.Claims
import io.jsonwebtoken.RequiredTypeException

fun Claims.toJwtData(): JwtData {
    return JwtData(
        accountId = getAccountId(this),
        authenticationMethod = this.get("authenticationMethod", String::class.java)
            .let { JwtAuthenticationMethod.valueOf(it) },
        roles = this.get("roles", arrayListOf<String>()::class.java).toSet()
    )
}

private fun getAccountId(from: Claims): Long {
    return try {
        from.get("accountId", java.lang.Long::class.java).toLong()
    } catch (e: RequiredTypeException) {
        from.get("accountId", java.lang.Integer::class.java).toLong()
    }
}
