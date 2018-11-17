package com.teamclicker.gameserver.framework.auth.jwt

import com.teamclicker.gameserver.Constants
import com.teamclicker.gameserver.framework.auth.jwt.mappers.toJwtData
import io.jsonwebtoken.Jwts
import mu.KLogging
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.stereotype.Service
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.*

@Service
class JwtAuthorizationFilter(
    private val jwtCryptoKeys: JwtCryptoKeys
) : AuthenticationWebFilter(BearerTokenReactiveAuthenticationManager()) {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val auth = extractCredentials(exchange.request)
        if (auth.isPresent) {
            SecurityContextHolder.getContext().authentication = auth.get()
        }
        return chain.filter(exchange)
    }

    private fun extractCredentials(req: ServerHttpRequest): Optional<Authentication> {
        val header = req.headers.getFirst(Constants.JWT_HEADER_NAME)

        return headerParser(header)
    }

    internal fun headerParser(header: String?): Optional<Authentication> {
        if (header === null || !header.startsWith(Constants.JWT_TOKEN_PREFIX)) {
            logger.trace { "Request has no '${Constants.JWT_HEADER_NAME}' header or it doesn't start with '${Constants.JWT_TOKEN_PREFIX} '" }
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

    companion object: KLogging()
}