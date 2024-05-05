package com.findmypronow.plugins

import com.findmypronow.session.ChatSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import java.io.File

fun Application.configureSessions() {
    install(Sessions) {
        cookie<ChatSession>("SESSION")
    }
    intercept(ApplicationCallPipeline.Call) {
        if (call.sessions.get<ChatSession>() == null) {
            val username = call.parameters["username"] ?: "Guest"
            call.sessions.set(ChatSession(username, generateNonce()))
        }
    }

    /*install(Sessions) {
        val secretEncryptKey = hex("00112233445566778899aabbccddeeff")
        val secretAuthKey = hex("02030405060708090a0b0c")
        cookie<UserSession>(
            name = "USER_SESSION",
            storage = directorySessionStorage(File(".sessions"))
        ) {
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretAuthKey))
        }
    }*/
}