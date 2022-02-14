package com.example.plugins

import com.example.authentication.JwtService
import com.example.repository.Repo
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlin.collections.set

fun Application.configureSecurity() {
    data class MySession(val count: Int = 0)
    val jwtService = JwtService()
    val db = Repo()
    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(Authentication) {

        jwt("jwt"){
            verifier(jwtService.verifier)
            realm = "Note Server"
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").asString()
                val user = db.findUserByEmail(email)
                user
            }
        }

    }


    routing {
        get("/") {
            call.respondText("Hello")
        }
    }
}
