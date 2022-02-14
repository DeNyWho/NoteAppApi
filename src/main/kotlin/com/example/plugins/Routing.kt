package com.example.plugins

import com.example.authentication.JwtService
import com.example.authentication.hash
import com.example.repository.DatabaseFactory
import com.example.repository.Repo
import com.example.routes.userRoutes
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    fun hashFunction(s: String) = hash(s)
    val jwtService = JwtService()
    val db = Repo()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        val hashFun = { s: String -> hash(s) }
        install(Locations)
        userRoutes(db, jwtService, hashFun)

        route("/notes") {


            route("/create") {

                //localhost:8000/notes/create
                post {
                    val body = call.receive<String>()
                    call.respond(body)
                }
            }

            delete {
                val body = call.receive<String>()
                call.respond(body)
            }
        }
    }
}
