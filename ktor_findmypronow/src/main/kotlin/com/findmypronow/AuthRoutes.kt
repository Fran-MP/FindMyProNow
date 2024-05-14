package com.findmypronow


import com.findmypronow.data.requests.AuthRequest
import com.findmypronow.data.responses.AuthResponse
import com.findmypronow.data.model.User
import com.findmypronow.data.model.UserDataSource
import com.findmypronow.security.hashing.HashingService
import com.findmypronow.security.hashing.SecurityHash

import com.findmypronow.security.token.TokenClaim
import com.findmypronow.security.token.TokenConfig
import com.findmypronow.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signUp(
    hashingService: HashingService,
    userDataSource: UserDataSource
) {
    post("signup"){
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val areFieldsBlank = request.username.isBlank() || request.password.isBlank()
        val isPasswordTooShort = request.password.length < 8
        if (areFieldsBlank || isPasswordTooShort) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val securityHash = hashingService.generateSecurityHash(request.password)
        val user = User(
            username = request.username,
            email = request.email,
            password = securityHash.hash,
            salt = securityHash.salt
        )
        val wasAcknowledged = userDataSource.insertUser(user)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict)
            return@post
        }
        call.respond(HttpStatusCode.OK)
    }

}

fun Route.signIn(
    tokenService: TokenService,
    tokenConfig: TokenConfig,
    hashingService: HashingService,
    userDataSource: UserDataSource
) {
    post("signin"){
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val user = userDataSource.getUserByUserName(request.username)
        if (user == null) {
            call.respond(HttpStatusCode.Conflict, "Nombre de usuario incorrecto")
            return@post
        }

        val isValidPassword = hashingService.verifyHash(
            value = request.password,
            securityHash = SecurityHash(
                hash = user.password,
                salt = user.salt
            )
        )
        /*if (!isValidPassword) {
            call.respond(HttpStatusCode.Conflict, "Contraseña incorrecta")
            return@post
        }*/

        val token = tokenService.generarToken(
            config = tokenConfig,
            TokenClaim(
                name = "userId",
                value = user.id.toString()
            )
        )
        call.respond(
            status = HttpStatusCode.OK,
            message = AuthResponse(
                token = token
            )
        )
    }
}

fun Route.authenticate(){
    get ("authenticate"){
        call.respond(HttpStatusCode.OK)
    }
}

/*fun Route.getSecretInfo(){
    authenticate{
        get ("secret"){
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            call.respond(HttpStatusCode.OK, "Tu Id es $userId")
        }
    }
}*/