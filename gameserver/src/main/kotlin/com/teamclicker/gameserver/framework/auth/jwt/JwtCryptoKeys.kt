package com.teamclicker.gameserver.framework.auth.jwt

import com.teamclicker.gameserver.Constants.JWT_PUBLIC_KEY_NAME
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec

@Service
class JwtCryptoKeys(private val resourceLoader: ResourceLoader) {
    final val JWT_PUBLIC_KEY: PublicKey

    init {
        JWT_PUBLIC_KEY = this.getPublicRSAKey(JWT_PUBLIC_KEY_NAME)
    }

    fun getPublicRSAKey(path: String): PublicKey {
        val keyBytes = resourceLoader.getResource(path).inputStream.readBytes()
        val keySpec = X509EncodedKeySpec(keyBytes)
        return KeyFactory.getInstance("RSA").generatePublic(keySpec)
    }
}