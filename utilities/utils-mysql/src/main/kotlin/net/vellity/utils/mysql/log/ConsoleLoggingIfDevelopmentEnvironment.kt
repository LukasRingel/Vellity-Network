package net.vellity.utils.mysql.log

import net.vellity.utils.configuration.Environment
import org.slf4j.LoggerFactory

class ConsoleLoggingIfDevelopmentEnvironment : QueryLogging {

  private val logging: QueryLogging = if (Environment.isDevelopmentEnvironment()) {
    ConsoleQueryLogging(LoggerFactory.getLogger("mysql-queries"))
  } else {
    DoNothingQueryLogging()
  }

  override fun logQuery(query: String) {
    logging.logQuery(query)
  }

  override fun logQueryError(query: String) {
    logging.logQuery("FAILED-QUERY: $query")
  }
}