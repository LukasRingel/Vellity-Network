package net.vellity.utils.mysql.migration

import net.vellity.utils.configuration.Environment

abstract class OnlyOnDevelopmentEnvironmentMigration : Migration {
  override fun shouldRun(): Boolean {
    return Environment.isDevelopmentEnvironment()
  }
}