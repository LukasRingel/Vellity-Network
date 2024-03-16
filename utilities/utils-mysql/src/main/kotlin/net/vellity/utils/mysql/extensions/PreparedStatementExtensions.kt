package net.vellity.utils.mysql.extensions

import net.vellity.utils.context.Context
import net.vellity.utils.context.Context.Companion.id
import java.sql.PreparedStatement
import java.sql.Timestamp
import java.time.Instant
import java.util.*

private val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

fun PreparedStatement.setContext(index: Int, context: Context) {
  this.setInt(index, context.id())
}

fun PreparedStatement.setUuid(index: Int, uuid: UUID) {
  this.setString(index, uuid.toString())
}

fun PreparedStatement.setInstant(index: Int, instant: Instant) {
  this.setTimestamp(index, Timestamp.from(instant), calendar)
}

fun PreparedStatement.setEnum(index: Int, enum: Enum<*>) {
  this.setInt(index, enum.ordinal)
}