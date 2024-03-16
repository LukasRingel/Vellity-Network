package net.vellity.utils.mysql.log

interface QueryLogging {
  fun logQuery(query: String)

  fun logQueryError(query: String)
}