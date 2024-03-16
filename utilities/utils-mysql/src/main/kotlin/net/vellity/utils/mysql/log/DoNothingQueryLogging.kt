package net.vellity.utils.mysql.log

class DoNothingQueryLogging: QueryLogging {
  override fun logQuery(query: String) {
    // do nothing
  }

  override fun logQueryError(query: String) {
    // do nothing
  }
}