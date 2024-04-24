package com.findmypronow.security.token

interface TokenService {
    fun generarToken(
        config: TokenConfig,
        vararg claims: TokenClaim
    ): String
}