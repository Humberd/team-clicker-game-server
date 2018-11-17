package com.teamclicker.gameserver.framework.config

import com.teamclicker.gameserver.Constants.JWT_HEADER_NAME
import com.teamclicker.gameserver.framework.auth.jwt.JwtAuthorizationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer

@EnableWebFluxSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
class WebSecurity(
    @Lazy private val jwtAuthorizationFilter: JwtAuthorizationFilter
): WebFluxConfigurer {

    @Bean
    fun chain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .addFilterAt(jwtAuthorizationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build()
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedHeaders(JWT_HEADER_NAME)
            .allowCredentials(false)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
    }

}
