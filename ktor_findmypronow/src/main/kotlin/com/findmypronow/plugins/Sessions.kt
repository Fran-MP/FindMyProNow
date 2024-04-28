package com.findmypronow.plugins

import com.findmypronow.session.ChatSession
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*

fun Application.configureSessions() {
    install(Sessions) {
        cookie<ChatSession>("SESSION")
    }
    intercept(ApplicationCallPipeline.Call) {
        if (call.sessions.get<ChatSession>() == null) {
            val usename = call.parameters["usename"] ?: "Guest"
            call.sessions.set(ChatSession(usename, generateNonce()))
        }
    }
}