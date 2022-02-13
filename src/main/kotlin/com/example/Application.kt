package com.example

import com.example.authentication.JwtService
import com.example.authentication.hash
import com.example.plugins.configureRouting
import com.example.plugins.configureSecurity
import com.example.plugins.configureSerialization
import com.example.repository.DatabaseFactory
import com.example.repository.Repo
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8000, host = "0.0.0.0") {
        DatabaseFactory.init()

        val db = Repo()
        val jwtService = JwtService()
        configureRouting()
        configureSecurity()
        configureSerialization()
    }.start(wait = true)
}
