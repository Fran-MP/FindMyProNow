package com.findmypronow.plugins


import com.findmypronow.authenticate
import com.findmypronow.data.user.UserDataSource
import com.findmypronow.room.RoomController
import com.findmypronow.routes.chatSocket
import com.findmypronow.routes.getAllMessages
//import com.findmypronow.getSecretInfo
import com.findmypronow.security.hashing.HashingService
import com.findmypronow.security.token.TokenConfig
import com.findmypronow.security.token.TokenService
import com.findmypronow.signIn
import com.findmypronow.signUp
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting(
    userDataSource: UserDataSource,
    hashingService: HashingService,
    tokenConfig: TokenConfig,
    tokenService: TokenService
) {
    routing {
        val roomController by inject<RoomController>()
        chatSocket(roomController)
        getAllMessages(roomController)
        signIn(tokenService,tokenConfig,hashingService,userDataSource)
        signUp(hashingService,userDataSource)
        authenticate()
        //getSecretInfo()
    }
}
