package com.findmypronow.plugins


import com.findmypronow.authenticate
import com.findmypronow.data.user.UserDataSource
import com.findmypronow.getSecretInfo
import com.findmypronow.security.hashing.HashingService
import com.findmypronow.security.token.TokenConfig
import com.findmypronow.security.token.TokenService
import com.findmypronow.signIn
import com.findmypronow.signUp
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenConfig: TokenConfig,
    tokenService: TokenService
) {
    routing {
        signIn(tokenService,tokenConfig,hashingService,userDataSource)
        signUp(hashingService,userDataSource)
        authenticate()
        getSecretInfo()
    }
}
