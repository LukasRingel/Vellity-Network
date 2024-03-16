package net.vellity.utils.mysql.supplied

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.vellity.utils.mysql.MySqlConnectionConfiguration
import java.sql.Connection

class HikariConnectionSupplier(private val configuration: MySqlConnectionConfiguration) : ConnectionSupplier {

  private lateinit var hikariDataSource: HikariDataSource

  override fun close() {
    hikariDataSource.close()
  }

  override fun finishConnection(connection: Connection) {
    connection.close()
  }

  override fun connect() {
    val hikariConfig = HikariConfig()
    hikariConfig.jdbcUrl = "jdbc:mariadb://${configuration.hostname}:${configuration.port}/${configuration.database}"
    hikariConfig.username = configuration.username
    hikariConfig.password = configuration.password
    hikariConfig.maximumPoolSize = configuration.maxPoolSize
    hikariDataSource = HikariDataSource(hikariConfig)
  }

  override fun get(): Connection {
    return hikariDataSource.connection
  }
}