package net.vellity.utils.mysql

import net.vellity.utils.configuration.ConfigurationFactory
import net.vellity.utils.configuration.Environment
import net.vellity.utils.mysql.log.ConsoleLoggingIfDevelopmentEnvironment
import net.vellity.utils.mysql.log.QueryLogging
import net.vellity.utils.mysql.supplied.HikariConnectionSupplier
import net.vellity.utils.mysql.supplied.SqlLiteConnectionSupplier
import net.vellity.utils.mysql.supplied.SuppliedMySqlConnection

object MySqlConnectionFactory {
  fun createMySqlConnection(configuration: MySqlConnectionConfiguration) =
    createMySqlConnection(configuration, ConsoleLoggingIfDevelopmentEnvironment())

  fun createMySqlConnection(configuration: MySqlConnectionConfiguration, logging: QueryLogging) =
    SuppliedMySqlConnection(chooseSupplier(configuration), logging)

  fun createMySqlConnection(pathToFile: String) = createMySqlConnection(
    ConfigurationFactory.createAndGet(
      pathToFile,
      MySqlConnectionConfiguration::class.java
    )
  )

  private fun chooseSupplier(configuration: MySqlConnectionConfiguration) =
    if (useSqlLite(configuration)) {
      SqlLiteConnectionSupplier()
    } else {
      HikariConnectionSupplier(configuration)
    }

  private fun useSqlLite(configuration: MySqlConnectionConfiguration) =
    Environment.isLocalDevelopment() &&
      configuration.hostname.equals("localhost", true)
}