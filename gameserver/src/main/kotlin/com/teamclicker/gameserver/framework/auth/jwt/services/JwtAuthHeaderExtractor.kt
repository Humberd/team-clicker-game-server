package com.teamclicker.gameserver.framework.auth.jwt.services

import com.teamclicker.gameserver.Constants
import com.teamclicker.gameserver.framework.auth.jwt.JwtAuthenticationToken
import com.teamclicker.gameserver.framework.auth.jwt.mappers.toJwtData
import io.jsonwebtoken.Jwts
import mu.KLogging
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtAuthHeaderExtractor(
    private val jwtCryptoKeys: JwtCryptoKeys
) {

    fun extractCredentials(accessor: StompHeaderAccessor): Optional<Authentication> {
        val header = accessor.getNativeHeader(Constants.JWT_HEADER_NAME)?.firstOrNull()

        return headerParser(header)
    }

    fun extractCredentials(req: ServerHttpRequest): Optional<Authentication> {
        val header = req.headers.getFirst(Constants.JWT_HEADER_NAME)

        return headerParser(header)
    }

    internal fun headerParser(header: String?): Optional<Authentication> {
        if (header === null || !header.startsWith(Constants.JWT_TOKEN_PREFIX)) {
            JwtAuthorizationFilter.logger.info { "Request has no '${Constants.JWT_HEADER_NAME}' header or it doesn't start with '${Constants.JWT_TOKEN_PREFIX} '" }
            return Optional.empty()
        }

        val jwtToken = header.replaceFirst(Constants.JWT_TOKEN_PREFIX, "")

        return Optional.of(getAuthentication(jwtToken))
    }

    internal fun getAuthentication(jwtToken: String): Authentication {
        val jwtClaims = Jwts.parser()
            .setSigningKey(jwtCryptoKeys.JWT_PUBLIC_KEY)
            .parseClaimsJws(jwtToken)
            .getBody()

        val jwtData = jwtClaims.toJwtData()

        return JwtAuthenticationToken(jwtData)
    }

    companion object : KLogging()
}