package com.example.plugins

import com.example.authentication.JwtService
import com.example.authentication.hash
import com.example.data.model.User
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    fun hashFunction(s: String) = hash(s)
    val jwtService = JwtService()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        get("/note/{id}"){
            val id = call.parameters["id"]
            call.respond("${id}")
        }

        get("/token"){
            val email = call.request.queryParameters["email"]!!
            val password = call.request.queryParameters["password"]!!
            val username = call.request.queryParameters["username"]!!

            val user = User(email,hashFunction(password),username)
            call.respond(jwtService.generateToken(user))

        }

        route("/notes"){


            route("/create"){

                //localhost:8000/notes/create
                post{
                    val body = call.receive<String>()
                    call.respond(body)
                }
            }

            delete{
                val body = call.receive<String>()
                call.respond(body)
            }
        }
    }
}
