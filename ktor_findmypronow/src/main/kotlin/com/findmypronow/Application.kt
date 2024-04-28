package com.findmypronow

import com.findmypronow.data.getDatabase
import com.findmypronow.data.user.MongoUserDataSource
import com.findmypronow.di.mainModule
import com.findmypronow.plugins.*
import com.findmypronow.security.hashing.HashingService_SHA256
import com.findmypronow.security.token.JwtTokenService
import com.findmypronow.security.token.TokenConfig
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    install(Koin) {
        modules(mainModule)
    }
    val database = getDatabase()

    val userDataSource = MongoUserDataSource(database)

    /*GlobalScope.launch {
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
    }*/

    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = HashingService_SHA256()

    configureSockets()
    configureSessions()
    configureRouting(userDataSource, hashingService, tokenConfig, tokenService)
    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenConfig)
}
