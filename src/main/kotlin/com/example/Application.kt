package com.example

import com.example.plugins.configureRouting
import com.example.plugins.configureSecurity
import com.example.plugins.configureSerialization
import com.example.repository.DatabaseFactory
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8000, host = "0.0.0.0") {
        DatabaseFactory.init()
        configureSecurity()
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
