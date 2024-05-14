package com.findmypronow

import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class ModuleTest {

    @Test
    fun testGetSecret() = testApplication {
        application {
            module()
        }
        client.get("/secret").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostSignin() = testApplication {
        application {
            module()
        }
        client.post("/signin").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostSignup() = testApplication {
        application {
            module()
        }
        client.post("/signup").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testWebsocketWs() = testApplication {
        application {
            module()
        }
        val client = createClient {
            install(WebSockets)
        }
        client.webSocket("/ws") {
            TODO("Please write your test here")
        }
    }
}