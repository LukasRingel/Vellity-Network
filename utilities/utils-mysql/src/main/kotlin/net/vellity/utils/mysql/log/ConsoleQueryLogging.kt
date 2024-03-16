package net.vellity.utils.mysql.log

import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger

class ConsoleQueryLogging(private val logger: Logger) : QueryLogging {
  override fun logQuery(query: String) {
    logger.info(StringUtils.normalizeSpace(query))
  }

  override fun logQueryError(query: String) {
    logger.info("FAILED-QUERY: " + StringUtils.normalizeSpace(query))
  }
}