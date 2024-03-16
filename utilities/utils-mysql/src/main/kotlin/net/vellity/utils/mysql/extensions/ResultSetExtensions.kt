package net.vellity.utils.mysql.extensions

import net.vellity.utils.context.Context
import java.sql.ResultSet
import java.time.Instant
import java.util.*

fun ResultSet.getContext(fieldName: String): Context {
  return Context.valueOf(this.getInt(fieldName))
}

fun ResultSet.getUuid(fieldName: String): UUID {
  return UUID.fromString(this.getString(fieldName))
}

fun ResultSet.getInstant(fieldName: String): Instant {
  return this.getTimestamp(fieldName).toInstant()
}