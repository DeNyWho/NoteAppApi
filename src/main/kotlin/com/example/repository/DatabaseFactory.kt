package com.example.repository

import com.example.data.table.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init (){
        Database.connect(hikari())

        transaction {
            SchemaUtils.create(UserTable)
        }
    }

    private fun hikari(): HikariDataSource{
        val config = HikariConfig()
        config.driverClassName = System.getenv("JDBC_DRIVER_1")
        config.jdbcUrl = System.getenv("JDBC_DATABASE_URL_1")
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()

        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}