package com.findmypronow.room

import io.ktor.websocket.*

data class Member(
    val username: String,
    val sessionIdval : String,
    val socket: WebSocketSession

)
