package com.findmypronow


import com.findmypronow.data.getDatabase
import com.findmypronow.data.user.MongoUserDataSource
import com.findmypronow.data.user.User
import com.findmypronow.plugins.*
import com.findmypronow.security.hashing.HashingService_SHA256
import com.findmypronow.security.token.JwtTokenService
import com.findmypronow.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


fun main() {

    val database = getDatabase()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)

            .start(wait = true)
    }

fun Application.module() {

    val database = getDatabase()





    val userDataSource = MongoUserDataSource(database)
    GlobalScope.launch {
        val user = User(
            username = "admin",
            password = "admin",
            salt = "salt",
            //phone = "623456789",
            //jobDescription = "Desarrollador",
            //job = "Desarrollado",
            //address = "Mazarrón",
            //name = "Francisco",
            //lastName = "Pereira",
            //role = "Proveedor",
            //email = "franjmpereira@gmail.com"
        )
        userDataSource.insertUser(user)
    }


    val tokenService= JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = HashingService_SHA256()


    configureRouting(userDataSource,hashingService,tokenConfig,tokenService)
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenConfig)

}
