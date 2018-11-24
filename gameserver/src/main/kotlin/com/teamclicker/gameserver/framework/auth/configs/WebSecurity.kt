package com.teamclicker.gameserver.framework.auth.configs

import com.teamclicker.gameserver.Constants.JWT_HEADER_NAME
import com.teamclicker.gameserver.framework.auth.jwt.BearerTokenReactiveAuthenticationManager
import com.teamclicker.gameserver.framework.auth.jwt.services.JwtAuthorizationFilter
import com.teamclicker.gameserver.framework.auth.jwt.resolvers.JwtResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
class WebSecurity(
    private val jwtAuthorizationFilter: JwtAuthorizationFilter
) : WebFluxConfigurer {

    @Bean
    fun chain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .addFilterAt(bearerAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
            .build()
    }

    /**
     * Use the already implemented logic by AuthenticationWebFilter and set a custom
     * converter that will handle requests containing a Bearer token inside
     * the HTTP Authorization header.
     * Set a dummy authentication manager to this filter, it's not needed because
     * the converter handles this.
     *
     * https://github.com/raphaelDL/spring-webflux-security-jwt/blob/master/src/main/java/io/rapha/spring/reactive/security/SecuredRestApplication.java
     *
     * @return bearerAuthenticationFilter that will authorize requests containing a JWT
     */
    private fun bearerAuthenticationFilter(): AuthenticationWebFilter {
        val bearerAuthenticationFilter =
            AuthenticationWebFilter(BearerTokenReactiveAuthenticationManager())
        val bearerConverter = jwtAuthorizationFilter

        bearerAuthenticationFilter.setAuthenticationConverter(bearerConverter)

        return bearerAuthenticationFilter
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedHeaders(JWT_HEADER_NAME)
            .allowCredentials(false)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
    }

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(JwtResolver())
    }
}
