package com.teamclicker.gameserver.framework.auth.jwt

import com.teamclicker.gameserver.Constants
import com.teamclicker.gameserver.framework.auth.jwt.mappers.toJwtData
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Service
class JwtAuthorizationFilter(
    authManager: AuthenticationManager,
    private val jwtCryptoKeys: JwtCryptoKeys
) : BasicAuthenticationFilter(authManager) {
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        val auth = extractCredentials(req)
        if (auth.isPresent) {
            SecurityContextHolder.getContext().authentication = auth.get()
        }
        chain.doFilter(req, res)
    }

    private fun extractCredentials(req: HttpServletRequest): Optional<Authentication> {
        val header = req.getHeader(Constants.JWT_HEADER_NAME)

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
}